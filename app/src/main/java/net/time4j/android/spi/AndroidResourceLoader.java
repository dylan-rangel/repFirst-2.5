package net.time4j.android.spi;

import android.content.Context;
import android.text.format.DateFormat;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import net.time4j.android.AssetLocation;
import net.time4j.base.ResourceLoader;
import net.time4j.calendar.service.GenericTextProviderSPI;
import net.time4j.calendar.service.KoreanExtension;
import net.time4j.engine.ChronoExtension;
import net.time4j.format.DisplayMode;
import net.time4j.format.FormatPatternProvider;
import net.time4j.format.NumberSymbolProvider;
import net.time4j.format.PluralProvider;
import net.time4j.format.TextProvider;
import net.time4j.format.UnitPatternProvider;
import net.time4j.format.WeekdataProvider;
import net.time4j.format.internal.ExtendedPatterns;
import net.time4j.i18n.DefaultPluralProviderSPI;
import net.time4j.i18n.HistoricExtension;
import net.time4j.i18n.IsoTextProviderSPI;
import net.time4j.i18n.SymbolProviderSPI;
import net.time4j.i18n.UnitPatternProviderSPI;
import net.time4j.i18n.WeekdataProviderSPI;
import net.time4j.scale.LeapSecondProvider;
import net.time4j.scale.TickProvider;
import net.time4j.tz.ZoneModelProvider;
import net.time4j.tz.ZoneNameProvider;
import net.time4j.tz.spi.TimezoneRepositoryProviderSPI;
import net.time4j.tz.spi.ZoneNameProviderSPI;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class AndroidResourceLoader extends ResourceLoader {
    private static final Set<String> MODULES;
    private static final Map<Class<?>, Iterable<?>> PROVIDERS;
    private Context context = null;
    private AssetLocation assetLocation = null;
    private List<FormatPatternProvider> patterns = Collections.emptyList();

    /* JADX WARN: Multi-variable type inference failed */
    private static <T> T cast(Object obj) {
        return obj;
    }

    static {
        HashMap hashMap = new HashMap();
        AnonymousClass1 anonymousClass1 = null;
        hashMap.put(TextProvider.class, new LazyTextdata(anonymousClass1));
        hashMap.put(ZoneModelProvider.class, new LazyZoneRules(anonymousClass1));
        hashMap.put(ZoneNameProvider.class, new LazyZoneNames(anonymousClass1));
        hashMap.put(LeapSecondProvider.class, new LazyLeapseconds(anonymousClass1));
        hashMap.put(ChronoExtension.class, new LazyExtensions(anonymousClass1));
        hashMap.put(NumberSymbolProvider.class, new LazyNumberSymbols(anonymousClass1));
        hashMap.put(PluralProvider.class, new LazyPluraldata(anonymousClass1));
        hashMap.put(UnitPatternProvider.class, Collections.singleton(new UnitPatternProviderSPI()));
        hashMap.put(WeekdataProvider.class, new LazyWeekdata(anonymousClass1));
        hashMap.put(TickProvider.class, Collections.singleton(new AndroidTickerSPI()));
        PROVIDERS = Collections.unmodifiableMap(hashMap);
        HashSet hashSet = new HashSet();
        hashSet.add("i18n");
        hashSet.add("calendar");
        hashSet.add("olson");
        hashSet.add("tzdata");
        MODULES = Collections.unmodifiableSet(hashSet);
    }

    public void init(Context context, AssetLocation assetLocation) {
        if (context == null) {
            throw new NullPointerException("Missing Android-context.");
        }
        this.context = context;
        this.assetLocation = assetLocation;
        this.patterns = Collections.singletonList(new AndroidFormatPatterns(this, null));
    }

    @Override // net.time4j.base.ResourceLoader
    public URI locate(String str, Class<?> cls, String str2) {
        try {
            if (MODULES.contains(str)) {
                return new URI("net/time4j/" + str + '/' + str2);
            }
            URL resource = cls.getClassLoader().getResource(str2);
            if (resource != null) {
                return resource.toURI();
            }
            return null;
        } catch (URISyntaxException unused) {
            return null;
        }
    }

    @Override // net.time4j.base.ResourceLoader
    public InputStream load(URI uri, boolean z) {
        if (uri == null) {
            return null;
        }
        try {
            if (uri.isAbsolute()) {
                URLConnection openConnection = uri.toURL().openConnection();
                openConnection.setUseCaches(false);
                return openConnection.getInputStream();
            }
            AssetLocation assetLocation = this.assetLocation;
            if (assetLocation != null) {
                return assetLocation.open(uri.toString());
            }
            Context context = this.context;
            if (context == null) {
                throw new IllegalStateException("'ApplicationStarter.initialize(context)' must be called first at app start.");
            }
            return context.getAssets().open(uri.toString());
        } catch (IOException | RuntimeException unused) {
            return null;
        }
    }

    @Override // net.time4j.base.ResourceLoader
    public <S> Iterable<S> services(Class<S> cls) {
        Iterable<?> iterable = PROVIDERS.get(cls);
        if (iterable == null) {
            if (cls == FormatPatternProvider.class) {
                iterable = this.patterns;
            } else {
                return ServiceLoader.load(cls, cls.getClassLoader());
            }
        }
        return (Iterable) cast(iterable);
    }

    private class AndroidFormatPatterns implements ExtendedPatterns {
        private AndroidFormatPatterns() {
        }

        /* synthetic */ AndroidFormatPatterns(AndroidResourceLoader androidResourceLoader, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // net.time4j.format.FormatPatternProvider
        public String getDatePattern(DisplayMode displayMode, Locale locale) {
            return getDelegate().getDatePattern(displayMode, locale);
        }

        @Override // net.time4j.format.FormatPatternProvider
        public String getTimePattern(DisplayMode displayMode, Locale locale) {
            return getTimePattern(displayMode, locale, false);
        }

        @Override // net.time4j.format.internal.ExtendedPatterns
        public String getTimePattern(DisplayMode displayMode, Locale locale, boolean z) {
            String timePattern = getDelegate().getTimePattern(displayMode, locale, z);
            if (Locale.getDefault().equals(locale)) {
                boolean z2 = (displayMode != DisplayMode.SHORT ? getDelegate().getTimePattern(DisplayMode.SHORT, locale) : timePattern).indexOf(97) == -1;
                boolean is24HourFormat = DateFormat.is24HourFormat(AndroidResourceLoader.this.context);
                if (is24HourFormat != z2) {
                    if (is24HourFormat) {
                        return to24HourFormat(timePattern).replace("  ", StringUtils.SPACE).trim();
                    }
                    String str = locale.getLanguage().equals("en") ? "b" : "B";
                    int i = AnonymousClass1.$SwitchMap$net$time4j$format$DisplayMode[displayMode.ordinal()];
                    if (i == 1) {
                        return "h:mm:ss " + str + " zzzz";
                    }
                    if (i == 2) {
                        return "h:mm:ss " + str + " z";
                    }
                    if (i == 3) {
                        return "h:mm:ss " + str;
                    }
                    return "h:mm " + str;
                }
            }
            return timePattern;
        }

        @Override // net.time4j.format.FormatPatternProvider
        public String getDateTimePattern(DisplayMode displayMode, DisplayMode displayMode2, Locale locale) {
            return getDelegate().getDateTimePattern(displayMode, displayMode2, locale);
        }

        @Override // net.time4j.format.FormatPatternProvider
        public String getIntervalPattern(Locale locale) {
            return getDelegate().getIntervalPattern(locale);
        }

        private String to24HourFormat(String str) {
            StringBuilder sb = new StringBuilder();
            int length = str.length();
            int i = 0;
            while (i < length) {
                char charAt = str.charAt(i);
                if (charAt == '\'') {
                    sb.append(charAt);
                    while (true) {
                        i++;
                        if (i >= length) {
                            break;
                        }
                        char charAt2 = str.charAt(i);
                        if (charAt2 == '\'') {
                            sb.append(charAt2);
                            int i2 = i + 1;
                            if (i2 < length && str.charAt(i2) == '\'') {
                                i = i2;
                            }
                        }
                        sb.append(charAt2);
                    }
                } else if (charAt == 'h') {
                    sb.append('H');
                } else if (charAt != 'a') {
                    sb.append(charAt);
                }
                i++;
            }
            return sb.toString();
        }

        private ExtendedPatterns getDelegate() {
            return I18nDataHolder.ISODATA;
        }
    }

    /* renamed from: net.time4j.android.spi.AndroidResourceLoader$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$time4j$format$DisplayMode;

        static {
            int[] iArr = new int[DisplayMode.values().length];
            $SwitchMap$net$time4j$format$DisplayMode = iArr;
            try {
                iArr[DisplayMode.FULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$time4j$format$DisplayMode[DisplayMode.LONG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$time4j$format$DisplayMode[DisplayMode.MEDIUM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static final class LazyNumberSymbols implements Iterable<NumberSymbolProvider> {
        private LazyNumberSymbols() {
        }

        /* synthetic */ LazyNumberSymbols(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // java.lang.Iterable
        public Iterator<NumberSymbolProvider> iterator() {
            return I18nDataHolder.SYMBOLS.iterator();
        }
    }

    private static final class LazyWeekdata implements Iterable<WeekdataProvider> {
        private LazyWeekdata() {
        }

        /* synthetic */ LazyWeekdata(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // java.lang.Iterable
        public Iterator<WeekdataProvider> iterator() {
            return I18nDataHolder.WEEKDATA.iterator();
        }
    }

    private static final class LazyTextdata implements Iterable<TextProvider> {
        private LazyTextdata() {
        }

        /* synthetic */ LazyTextdata(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // java.lang.Iterable
        public Iterator<TextProvider> iterator() {
            return I18nDataHolder.TEXTDATA.iterator();
        }
    }

    private static final class LazyZoneRules implements Iterable<ZoneModelProvider> {
        private LazyZoneRules() {
        }

        /* synthetic */ LazyZoneRules(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // java.lang.Iterable
        public Iterator<ZoneModelProvider> iterator() {
            return ZoneDataHolder.RULES.iterator();
        }
    }

    private static final class LazyZoneNames implements Iterable<ZoneNameProvider> {
        private LazyZoneNames() {
        }

        /* synthetic */ LazyZoneNames(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // java.lang.Iterable
        public Iterator<ZoneNameProvider> iterator() {
            return ZoneDataHolder.NAMES.iterator();
        }
    }

    private static final class LazyLeapseconds implements Iterable<LeapSecondProvider> {
        private LazyLeapseconds() {
        }

        /* synthetic */ LazyLeapseconds(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // java.lang.Iterable
        public Iterator<LeapSecondProvider> iterator() {
            return ZoneDataHolder.LEAPSECONDS.iterator();
        }
    }

    private static final class LazyPluraldata implements Iterable<PluralProvider> {
        private LazyPluraldata() {
        }

        /* synthetic */ LazyPluraldata(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // java.lang.Iterable
        public Iterator<PluralProvider> iterator() {
            return StatelessIterables.PLURALS.iterator();
        }
    }

    private static final class LazyExtensions implements Iterable<ChronoExtension> {
        private LazyExtensions() {
        }

        /* synthetic */ LazyExtensions(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // java.lang.Iterable
        public Iterator<ChronoExtension> iterator() {
            return StatelessIterables.EXTENSIONS.iterator();
        }
    }

    private static final class I18nDataHolder {
        private static final IsoTextProviderSPI ISODATA;
        private static final Iterable<NumberSymbolProvider> SYMBOLS;
        private static final Iterable<TextProvider> TEXTDATA;
        private static final Iterable<WeekdataProvider> WEEKDATA;

        private I18nDataHolder() {
        }

        static {
            IsoTextProviderSPI isoTextProviderSPI = new IsoTextProviderSPI();
            ISODATA = isoTextProviderSPI;
            SYMBOLS = Collections.singleton(SymbolProviderSPI.INSTANCE);
            WEEKDATA = Collections.singletonList(new WeekdataProviderSPI());
            TEXTDATA = Collections.unmodifiableList(Arrays.asList(isoTextProviderSPI, new GenericTextProviderSPI()));
        }
    }

    private static final class ZoneDataHolder {
        private static final Iterable<LeapSecondProvider> LEAPSECONDS;
        private static final Iterable<ZoneNameProvider> NAMES;
        private static final Iterable<ZoneModelProvider> RULES;

        private ZoneDataHolder() {
        }

        static {
            LeapSecondProvider leapSecondProvider;
            Set singleton = Collections.singleton(new TimezoneRepositoryProviderSPI());
            RULES = singleton;
            NAMES = Collections.singleton(new ZoneNameProviderSPI());
            Iterator it = singleton.iterator();
            while (true) {
                if (!it.hasNext()) {
                    leapSecondProvider = null;
                    break;
                }
                ZoneModelProvider zoneModelProvider = (ZoneModelProvider) it.next();
                if (zoneModelProvider instanceof LeapSecondProvider) {
                    leapSecondProvider = (LeapSecondProvider) LeapSecondProvider.class.cast(zoneModelProvider);
                    break;
                }
            }
            if (leapSecondProvider == null) {
                LEAPSECONDS = Collections.emptyList();
            } else {
                LEAPSECONDS = Collections.singleton(leapSecondProvider);
            }
        }
    }

    private static final class StatelessIterables {
        private static final Iterable<PluralProvider> PLURALS = Collections.singleton(new DefaultPluralProviderSPI());
        private static final Iterable<ChronoExtension> EXTENSIONS = Arrays.asList(new HistoricExtension(), new KoreanExtension());

        private StatelessIterables() {
        }
    }
}
