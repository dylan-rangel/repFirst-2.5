package io.sentry;

import io.sentry.SentryOptions;
import io.sentry.protocol.Contexts;
import io.sentry.protocol.Request;
import io.sentry.protocol.TransactionNameSource;
import io.sentry.protocol.User;
import io.sentry.util.CollectionUtils;
import io.sentry.util.Objects;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes3.dex */
public final class Scope {
    private List<Attachment> attachments;
    private final Queue<Breadcrumb> breadcrumbs;
    private Contexts contexts;
    private List<EventProcessor> eventProcessors;
    private Map<String, Object> extra;
    private List<String> fingerprint;
    private SentryLevel level;
    private final SentryOptions options;
    private PropagationContext propagationContext;
    private final Object propagationContextLock;
    private Request request;
    private volatile Session session;
    private final Object sessionLock;
    private Map<String, String> tags;
    private ITransaction transaction;
    private final Object transactionLock;
    private String transactionName;
    private User user;

    public interface IWithPropagationContext {
        void accept(PropagationContext propagationContext);
    }

    interface IWithSession {
        void accept(Session session);
    }

    public interface IWithTransaction {
        void accept(ITransaction iTransaction);
    }

    public Scope(SentryOptions sentryOptions) {
        this.fingerprint = new ArrayList();
        this.tags = new ConcurrentHashMap();
        this.extra = new ConcurrentHashMap();
        this.eventProcessors = new CopyOnWriteArrayList();
        this.sessionLock = new Object();
        this.transactionLock = new Object();
        this.propagationContextLock = new Object();
        this.contexts = new Contexts();
        this.attachments = new CopyOnWriteArrayList();
        SentryOptions sentryOptions2 = (SentryOptions) Objects.requireNonNull(sentryOptions, "SentryOptions is required.");
        this.options = sentryOptions2;
        this.breadcrumbs = createBreadcrumbsList(sentryOptions2.getMaxBreadcrumbs());
        this.propagationContext = new PropagationContext();
    }

    public Scope(Scope scope) {
        this.fingerprint = new ArrayList();
        this.tags = new ConcurrentHashMap();
        this.extra = new ConcurrentHashMap();
        this.eventProcessors = new CopyOnWriteArrayList();
        this.sessionLock = new Object();
        this.transactionLock = new Object();
        this.propagationContextLock = new Object();
        this.contexts = new Contexts();
        this.attachments = new CopyOnWriteArrayList();
        this.transaction = scope.transaction;
        this.transactionName = scope.transactionName;
        this.session = scope.session;
        this.options = scope.options;
        this.level = scope.level;
        User user = scope.user;
        this.user = user != null ? new User(user) : null;
        Request request = scope.request;
        this.request = request != null ? new Request(request) : null;
        this.fingerprint = new ArrayList(scope.fingerprint);
        this.eventProcessors = new CopyOnWriteArrayList(scope.eventProcessors);
        Breadcrumb[] breadcrumbArr = (Breadcrumb[]) scope.breadcrumbs.toArray(new Breadcrumb[0]);
        Queue<Breadcrumb> createBreadcrumbsList = createBreadcrumbsList(scope.options.getMaxBreadcrumbs());
        for (Breadcrumb breadcrumb : breadcrumbArr) {
            createBreadcrumbsList.add(new Breadcrumb(breadcrumb));
        }
        this.breadcrumbs = createBreadcrumbsList;
        Map<String, String> map = scope.tags;
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry != null) {
                concurrentHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        this.tags = concurrentHashMap;
        Map<String, Object> map2 = scope.extra;
        ConcurrentHashMap concurrentHashMap2 = new ConcurrentHashMap();
        for (Map.Entry<String, Object> entry2 : map2.entrySet()) {
            if (entry2 != null) {
                concurrentHashMap2.put(entry2.getKey(), entry2.getValue());
            }
        }
        this.extra = concurrentHashMap2;
        this.contexts = new Contexts(scope.contexts);
        this.attachments = new CopyOnWriteArrayList(scope.attachments);
        this.propagationContext = new PropagationContext(scope.propagationContext);
    }

    public SentryLevel getLevel() {
        return this.level;
    }

    public void setLevel(SentryLevel sentryLevel) {
        this.level = sentryLevel;
        Iterator<IScopeObserver> it = this.options.getScopeObservers().iterator();
        while (it.hasNext()) {
            it.next().setLevel(sentryLevel);
        }
    }

    public String getTransactionName() {
        ITransaction iTransaction = this.transaction;
        return iTransaction != null ? iTransaction.getName() : this.transactionName;
    }

    public void setTransaction(String str) {
        if (str != null) {
            ITransaction iTransaction = this.transaction;
            if (iTransaction != null) {
                iTransaction.setName(str, TransactionNameSource.CUSTOM);
            }
            this.transactionName = str;
            Iterator<IScopeObserver> it = this.options.getScopeObservers().iterator();
            while (it.hasNext()) {
                it.next().setTransaction(str);
            }
            return;
        }
        this.options.getLogger().log(SentryLevel.WARNING, "Transaction cannot be null", new Object[0]);
    }

    public ISpan getSpan() {
        Span latestActiveSpan;
        ITransaction iTransaction = this.transaction;
        return (iTransaction == null || (latestActiveSpan = iTransaction.getLatestActiveSpan()) == null) ? iTransaction : latestActiveSpan;
    }

    public void setTransaction(ITransaction iTransaction) {
        synchronized (this.transactionLock) {
            this.transaction = iTransaction;
            for (IScopeObserver iScopeObserver : this.options.getScopeObservers()) {
                if (iTransaction != null) {
                    iScopeObserver.setTransaction(iTransaction.getName());
                    iScopeObserver.setTrace(iTransaction.getSpanContext());
                } else {
                    iScopeObserver.setTransaction(null);
                    iScopeObserver.setTrace(null);
                }
            }
        }
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
        Iterator<IScopeObserver> it = this.options.getScopeObservers().iterator();
        while (it.hasNext()) {
            it.next().setUser(user);
        }
    }

    public Request getRequest() {
        return this.request;
    }

    public void setRequest(Request request) {
        this.request = request;
        Iterator<IScopeObserver> it = this.options.getScopeObservers().iterator();
        while (it.hasNext()) {
            it.next().setRequest(request);
        }
    }

    public List<String> getFingerprint() {
        return this.fingerprint;
    }

    public void setFingerprint(List<String> list) {
        if (list == null) {
            return;
        }
        this.fingerprint = new ArrayList(list);
        Iterator<IScopeObserver> it = this.options.getScopeObservers().iterator();
        while (it.hasNext()) {
            it.next().setFingerprint(list);
        }
    }

    public Queue<Breadcrumb> getBreadcrumbs() {
        return this.breadcrumbs;
    }

    private Breadcrumb executeBeforeBreadcrumb(SentryOptions.BeforeBreadcrumbCallback beforeBreadcrumbCallback, Breadcrumb breadcrumb, Hint hint) {
        try {
            return beforeBreadcrumbCallback.execute(breadcrumb, hint);
        } catch (Throwable th) {
            this.options.getLogger().log(SentryLevel.ERROR, "The BeforeBreadcrumbCallback callback threw an exception. Exception details will be added to the breadcrumb.", th);
            if (th.getMessage() == null) {
                return breadcrumb;
            }
            breadcrumb.setData("sentry:message", th.getMessage());
            return breadcrumb;
        }
    }

    public void addBreadcrumb(Breadcrumb breadcrumb, Hint hint) {
        if (breadcrumb == null) {
            return;
        }
        if (hint == null) {
            hint = new Hint();
        }
        SentryOptions.BeforeBreadcrumbCallback beforeBreadcrumb = this.options.getBeforeBreadcrumb();
        if (beforeBreadcrumb != null) {
            breadcrumb = executeBeforeBreadcrumb(beforeBreadcrumb, breadcrumb, hint);
        }
        if (breadcrumb != null) {
            this.breadcrumbs.add(breadcrumb);
            for (IScopeObserver iScopeObserver : this.options.getScopeObservers()) {
                iScopeObserver.addBreadcrumb(breadcrumb);
                iScopeObserver.setBreadcrumbs(this.breadcrumbs);
            }
            return;
        }
        this.options.getLogger().log(SentryLevel.INFO, "Breadcrumb was dropped by beforeBreadcrumb", new Object[0]);
    }

    public void addBreadcrumb(Breadcrumb breadcrumb) {
        addBreadcrumb(breadcrumb, null);
    }

    public void clearBreadcrumbs() {
        this.breadcrumbs.clear();
        Iterator<IScopeObserver> it = this.options.getScopeObservers().iterator();
        while (it.hasNext()) {
            it.next().setBreadcrumbs(this.breadcrumbs);
        }
    }

    public void clearTransaction() {
        synchronized (this.transactionLock) {
            this.transaction = null;
        }
        this.transactionName = null;
        for (IScopeObserver iScopeObserver : this.options.getScopeObservers()) {
            iScopeObserver.setTransaction(null);
            iScopeObserver.setTrace(null);
        }
    }

    public ITransaction getTransaction() {
        return this.transaction;
    }

    public void clear() {
        this.level = null;
        this.user = null;
        this.request = null;
        this.fingerprint.clear();
        clearBreadcrumbs();
        this.tags.clear();
        this.extra.clear();
        this.eventProcessors.clear();
        clearTransaction();
        clearAttachments();
    }

    public Map<String, String> getTags() {
        return CollectionUtils.newConcurrentHashMap(this.tags);
    }

    public void setTag(String str, String str2) {
        this.tags.put(str, str2);
        for (IScopeObserver iScopeObserver : this.options.getScopeObservers()) {
            iScopeObserver.setTag(str, str2);
            iScopeObserver.setTags(this.tags);
        }
    }

    public void removeTag(String str) {
        this.tags.remove(str);
        for (IScopeObserver iScopeObserver : this.options.getScopeObservers()) {
            iScopeObserver.removeTag(str);
            iScopeObserver.setTags(this.tags);
        }
    }

    public Map<String, Object> getExtras() {
        return this.extra;
    }

    public void setExtra(String str, String str2) {
        this.extra.put(str, str2);
        for (IScopeObserver iScopeObserver : this.options.getScopeObservers()) {
            iScopeObserver.setExtra(str, str2);
            iScopeObserver.setExtras(this.extra);
        }
    }

    public void removeExtra(String str) {
        this.extra.remove(str);
        for (IScopeObserver iScopeObserver : this.options.getScopeObservers()) {
            iScopeObserver.removeExtra(str);
            iScopeObserver.setExtras(this.extra);
        }
    }

    public Contexts getContexts() {
        return this.contexts;
    }

    public void setContexts(String str, Object obj) {
        this.contexts.put(str, obj);
        Iterator<IScopeObserver> it = this.options.getScopeObservers().iterator();
        while (it.hasNext()) {
            it.next().setContexts(this.contexts);
        }
    }

    public void setContexts(String str, Boolean bool) {
        HashMap hashMap = new HashMap();
        hashMap.put("value", bool);
        setContexts(str, hashMap);
    }

    public void setContexts(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("value", str2);
        setContexts(str, hashMap);
    }

    public void setContexts(String str, Number number) {
        HashMap hashMap = new HashMap();
        hashMap.put("value", number);
        setContexts(str, hashMap);
    }

    public void setContexts(String str, Collection<?> collection) {
        HashMap hashMap = new HashMap();
        hashMap.put("value", collection);
        setContexts(str, hashMap);
    }

    public void setContexts(String str, Object[] objArr) {
        HashMap hashMap = new HashMap();
        hashMap.put("value", objArr);
        setContexts(str, hashMap);
    }

    public void setContexts(String str, Character ch) {
        HashMap hashMap = new HashMap();
        hashMap.put("value", ch);
        setContexts(str, hashMap);
    }

    public void removeContexts(String str) {
        this.contexts.remove(str);
    }

    List<Attachment> getAttachments() {
        return new CopyOnWriteArrayList(this.attachments);
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
    }

    public void clearAttachments() {
        this.attachments.clear();
    }

    private Queue<Breadcrumb> createBreadcrumbsList(int i) {
        return SynchronizedQueue.synchronizedQueue(new CircularFifoQueue(i));
    }

    List<EventProcessor> getEventProcessors() {
        return this.eventProcessors;
    }

    public void addEventProcessor(EventProcessor eventProcessor) {
        this.eventProcessors.add(eventProcessor);
    }

    Session withSession(IWithSession iWithSession) {
        Session m240clone;
        synchronized (this.sessionLock) {
            iWithSession.accept(this.session);
            m240clone = this.session != null ? this.session.m240clone() : null;
        }
        return m240clone;
    }

    SessionPair startSession() {
        SessionPair sessionPair;
        synchronized (this.sessionLock) {
            if (this.session != null) {
                this.session.end();
            }
            Session session = this.session;
            sessionPair = null;
            if (this.options.getRelease() != null) {
                this.session = new Session(this.options.getDistinctId(), this.user, this.options.getEnvironment(), this.options.getRelease());
                sessionPair = new SessionPair(this.session.m240clone(), session != null ? session.m240clone() : null);
            } else {
                this.options.getLogger().log(SentryLevel.WARNING, "Release is not set on SentryOptions. Session could not be started", new Object[0]);
            }
        }
        return sessionPair;
    }

    static final class SessionPair {
        private final Session current;
        private final Session previous;

        public SessionPair(Session session, Session session2) {
            this.current = session;
            this.previous = session2;
        }

        public Session getPrevious() {
            return this.previous;
        }

        public Session getCurrent() {
            return this.current;
        }
    }

    Session endSession() {
        Session session;
        synchronized (this.sessionLock) {
            session = null;
            if (this.session != null) {
                this.session.end();
                Session m240clone = this.session.m240clone();
                this.session = null;
                session = m240clone;
            }
        }
        return session;
    }

    public void withTransaction(IWithTransaction iWithTransaction) {
        synchronized (this.transactionLock) {
            iWithTransaction.accept(this.transaction);
        }
    }

    SentryOptions getOptions() {
        return this.options;
    }

    public Session getSession() {
        return this.session;
    }

    public void setPropagationContext(PropagationContext propagationContext) {
        this.propagationContext = propagationContext;
    }

    public PropagationContext getPropagationContext() {
        return this.propagationContext;
    }

    public PropagationContext withPropagationContext(IWithPropagationContext iWithPropagationContext) {
        PropagationContext propagationContext;
        synchronized (this.propagationContextLock) {
            iWithPropagationContext.accept(this.propagationContext);
            propagationContext = new PropagationContext(this.propagationContext);
        }
        return propagationContext;
    }
}
