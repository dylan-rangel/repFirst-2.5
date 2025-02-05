package com.swmansion.rnscreens;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import com.facebook.react.views.textinput.ReactTextInputShadowNode;
import com.henninghall.date_picker.props.TextColorProp;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SearchViewFormatter.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0015\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u001fJ\u0015\u0010 \u001a\u00020\u001d2\b\u0010!\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u001fJ\u0016\u0010\"\u001a\u00020\u001d2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&J\u0015\u0010'\u001a\u00020\u001d2\b\u0010(\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u001fJ\u0015\u0010)\u001a\u00020\u001d2\b\u0010*\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u001fR\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0007R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\n \f*\u0004\u0018\u00010\u000b0\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\n \f*\u0004\u0018\u00010\u000b0\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000eR\u001c\u0010\u0015\u001a\n \f*\u0004\u0018\u00010\u00160\u00168BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u0004¨\u0006+"}, d2 = {"Lcom/swmansion/rnscreens/SearchViewFormatter;", "", "searchView", "Landroidx/appcompat/widget/SearchView;", "(Landroidx/appcompat/widget/SearchView;)V", "mDefaultTextColor", "", "Ljava/lang/Integer;", "mDefaultTintBackground", "Landroid/graphics/drawable/Drawable;", "searchCloseIcon", "Landroid/widget/ImageView;", "kotlin.jvm.PlatformType", "getSearchCloseIcon", "()Landroid/widget/ImageView;", "searchEditText", "Landroid/widget/EditText;", "getSearchEditText", "()Landroid/widget/EditText;", "searchIcon", "getSearchIcon", "searchTextPlate", "Landroid/view/View;", "getSearchTextPlate", "()Landroid/view/View;", "getSearchView", "()Landroidx/appcompat/widget/SearchView;", "setSearchView", "setHeaderIconColor", "", "headerIconColor", "(Ljava/lang/Integer;)V", "setHintTextColor", "hintTextColor", "setPlaceholder", ReactTextInputShadowNode.PROP_PLACEHOLDER, "", "shouldShowHintSearchIcon", "", "setTextColor", TextColorProp.name, "setTintColor", "tintColor", "react-native-screens_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class SearchViewFormatter {
    private Integer mDefaultTextColor;
    private Drawable mDefaultTintBackground;
    private SearchView searchView;

    public SearchViewFormatter(SearchView searchView) {
        Intrinsics.checkNotNullParameter(searchView, "searchView");
        this.searchView = searchView;
    }

    public final SearchView getSearchView() {
        return this.searchView;
    }

    public final void setSearchView(SearchView searchView) {
        Intrinsics.checkNotNullParameter(searchView, "<set-?>");
        this.searchView = searchView;
    }

    private final EditText getSearchEditText() {
        View findViewById = this.searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        if (findViewById instanceof EditText) {
            return (EditText) findViewById;
        }
        return null;
    }

    private final View getSearchTextPlate() {
        return this.searchView.findViewById(androidx.appcompat.R.id.search_plate);
    }

    private final ImageView getSearchIcon() {
        return (ImageView) this.searchView.findViewById(androidx.appcompat.R.id.search_button);
    }

    private final ImageView getSearchCloseIcon() {
        return (ImageView) this.searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
    }

    public final void setTextColor(Integer textColor) {
        EditText searchEditText;
        ColorStateList textColors;
        Integer num = this.mDefaultTextColor;
        if (textColor == null) {
            if (num == null || (searchEditText = getSearchEditText()) == null) {
                return;
            }
            searchEditText.setTextColor(num.intValue());
            return;
        }
        if (num == null) {
            EditText searchEditText2 = getSearchEditText();
            Integer num2 = null;
            if (searchEditText2 != null && (textColors = searchEditText2.getTextColors()) != null) {
                num2 = Integer.valueOf(textColors.getDefaultColor());
            }
            this.mDefaultTextColor = num2;
        }
        EditText searchEditText3 = getSearchEditText();
        if (searchEditText3 == null) {
            return;
        }
        searchEditText3.setTextColor(textColor.intValue());
    }

    public final void setTintColor(Integer tintColor) {
        Drawable drawable = this.mDefaultTintBackground;
        if (tintColor != null) {
            if (drawable == null) {
                this.mDefaultTintBackground = getSearchTextPlate().getBackground();
            }
            getSearchTextPlate().setBackgroundColor(tintColor.intValue());
        } else if (drawable != null) {
            getSearchTextPlate().setBackground(drawable);
        }
    }

    public final void setHeaderIconColor(Integer headerIconColor) {
        if (headerIconColor == null) {
            return;
        }
        int intValue = headerIconColor.intValue();
        getSearchIcon().setColorFilter(intValue);
        getSearchCloseIcon().setColorFilter(intValue);
    }

    public final void setHintTextColor(Integer hintTextColor) {
        if (hintTextColor == null) {
            return;
        }
        int intValue = hintTextColor.intValue();
        EditText searchEditText = getSearchEditText();
        if (searchEditText == null) {
            return;
        }
        searchEditText.setHintTextColor(intValue);
    }

    public final void setPlaceholder(String placeholder, boolean shouldShowHintSearchIcon) {
        Intrinsics.checkNotNullParameter(placeholder, "placeholder");
        if (shouldShowHintSearchIcon) {
            this.searchView.setQueryHint(placeholder);
            return;
        }
        EditText searchEditText = getSearchEditText();
        if (searchEditText == null) {
            return;
        }
        searchEditText.setHint(placeholder);
    }
}
