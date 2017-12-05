package com.tongtong.permissionlib;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by allen on 2017/10/13.
 */

public abstract class AlertDialog {
    public AlertDialog() {
    }

    public static AlertDialog.Builder newBuilder(Context context) {
        return (AlertDialog.Builder)(Build.VERSION.SDK_INT >= 21?new AlertDialog.APi21Builder(context):new AlertDialog.Api20Builder(context));
    }

    public static AlertDialog.Builder newBuilder(Context context, int themeResId) {
        return (AlertDialog.Builder)(Build.VERSION.SDK_INT >= 21?new AlertDialog.APi21Builder(context, themeResId):new AlertDialog.Api20Builder(context, themeResId));
    }

    /** @deprecated */
    @Deprecated
    public static AlertDialog.Builder build(Context context) {
        return newBuilder(context);
    }

    /** @deprecated */
    public static AlertDialog.Builder build(Context context, int themeResId) {
        return newBuilder(context, themeResId);
    }

    public abstract void show();

    public abstract void dismiss();

    public abstract boolean isShowing();

    public abstract void cancel();

    public abstract Button getButton(int var1);

    @Nullable
    public abstract ListView getListView();

    @NonNull
    public abstract Context getContext();

    @Nullable
    public abstract View getCurrentFocus();

    @NonNull
    public abstract LayoutInflater getLayoutInflater();

    @Nullable
    public abstract Activity getOwnerActivity();

    public abstract int getVolumeControlStream();

    @Nullable
    public abstract Window getWindow();

    private static class Api20Builder implements AlertDialog.Builder {
        private android.support.v7.app.AlertDialog.Builder builder;

        private Api20Builder(@NonNull Context context) {
            this(context, 0);
        }

        private Api20Builder(@NonNull Context context, @StyleRes int themeResId) {
            this.builder = new android.support.v7.app.AlertDialog.Builder(context, themeResId);
        }

        @NonNull
        public Context getContext() {
            return this.builder.getContext();
        }

        public AlertDialog.Builder setTitle(@StringRes int titleId) {
            this.builder.setTitle(titleId);
            return this;
        }

        public AlertDialog.Builder setTitle(CharSequence title) {
            this.builder.setTitle(title);
            return this;
        }

        public AlertDialog.Builder setCustomTitle(View customTitleView) {
            this.builder.setCustomTitle(customTitleView);
            return this;
        }

        public AlertDialog.Builder setMessage(@StringRes int messageId) {
            this.builder.setMessage(messageId);
            return this;
        }

        public AlertDialog.Builder setMessage(CharSequence message) {
            this.builder.setMessage(message);
            return this;
        }

        public AlertDialog.Builder setIcon(@DrawableRes int iconId) {
            this.builder.setIcon(iconId);
            return this;
        }

        public AlertDialog.Builder setIcon(Drawable icon) {
            this.builder.setIcon(icon);
            return this;
        }

        public AlertDialog.Builder setIconAttribute(@AttrRes int attrId) {
            this.builder.setIconAttribute(attrId);
            return this;
        }

        public AlertDialog.Builder setPositiveButton(@StringRes int textId, DialogInterface.OnClickListener listener) {
            this.builder.setPositiveButton(textId, listener);
            return this;
        }

        public AlertDialog.Builder setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
            this.builder.setPositiveButton(text, listener);
            return this;
        }

        public AlertDialog.Builder setNegativeButton(@StringRes int textId, DialogInterface.OnClickListener listener) {
            this.builder.setNegativeButton(textId, listener);
            return this;
        }

        public AlertDialog.Builder setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) {
            this.builder.setNegativeButton(text, listener);
            return this;
        }

        public AlertDialog.Builder setNeutralButton(@StringRes int textId, DialogInterface.OnClickListener listener) {
            this.builder.setNeutralButton(textId, listener);
            return this;
        }

        public AlertDialog.Builder setNeutralButton(CharSequence text, DialogInterface.OnClickListener listener) {
            this.builder.setNeutralButton(text, listener);
            return this;
        }

        public AlertDialog.Builder setCancelable(boolean cancelable) {
            this.builder.setCancelable(cancelable);
            return this;
        }

        public AlertDialog.Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.builder.setOnCancelListener(onCancelListener);
            return this;
        }

        public AlertDialog.Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.builder.setOnDismissListener(onDismissListener);
            return this;
        }

        public AlertDialog.Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            this.builder.setOnKeyListener(onKeyListener);
            return this;
        }

        public AlertDialog.Builder setItems(@ArrayRes int itemsId, DialogInterface.OnClickListener listener) {
            this.builder.setItems(itemsId, listener);
            return this;
        }

        public AlertDialog.Builder setItems(CharSequence[] items, DialogInterface.OnClickListener listener) {
            this.builder.setItems(items, listener);
            return this;
        }

        public AlertDialog.Builder setAdapter(ListAdapter adapter, DialogInterface.OnClickListener listener) {
            this.builder.setAdapter(adapter, listener);
            return this;
        }

        public AlertDialog.Builder setCursor(Cursor cursor, DialogInterface.OnClickListener listener, String labelColumn) {
            this.builder.setCursor(cursor, listener, labelColumn);
            return this;
        }

        public AlertDialog.Builder setMultiChoiceItems(@ArrayRes int itemsId, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
            this.builder.setMultiChoiceItems(itemsId, checkedItems, listener);
            return this;
        }

        public AlertDialog.Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
            this.builder.setMultiChoiceItems(items, checkedItems, listener);
            return this;
        }

        public AlertDialog.Builder setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn, DialogInterface.OnMultiChoiceClickListener listener) {
            this.builder.setMultiChoiceItems(cursor, isCheckedColumn, labelColumn, listener);
            return this;
        }

        public AlertDialog.Builder setSingleChoiceItems(@ArrayRes int itemsId, int checkedItem, DialogInterface.OnClickListener listener) {
            this.builder.setSingleChoiceItems(itemsId, checkedItem, listener);
            return this;
        }

        public AlertDialog.Builder setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn, DialogInterface.OnClickListener listener) {
            this.builder.setSingleChoiceItems(cursor, checkedItem, labelColumn, listener);
            return this;
        }

        public AlertDialog.Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, DialogInterface.OnClickListener listener) {
            this.builder.setSingleChoiceItems(items, checkedItem, listener);
            return this;
        }

        public AlertDialog.Builder setSingleChoiceItems(ListAdapter adapter, int checkedItem, DialogInterface.OnClickListener listener) {
            this.builder.setSingleChoiceItems(adapter, checkedItem, listener);
            return this;
        }

        public AlertDialog.Builder setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
            this.builder.setOnItemSelectedListener(listener);
            return this;
        }

        public AlertDialog.Builder setView(int layoutResId) {
            this.builder.setView(layoutResId);
            return this;
        }

        public AlertDialog.Builder setView(View view) {
            this.builder.setView(view);
            return this;
        }

        public AlertDialog create() {
            return new AlertDialog.Api20Dialog(this.builder.create());
        }

        public AlertDialog show() {
            AlertDialog dialog = this.create();
            dialog.show();
            return dialog;
        }
    }

    private static class APi21Builder implements AlertDialog.Builder {
        private android.app.AlertDialog.Builder builder;

        private APi21Builder(@NonNull Context context) {
            this(context, 0);
        }

        private APi21Builder(@NonNull Context context, @StyleRes int themeResId) {
            this.builder = new android.app.AlertDialog.Builder(context, themeResId);
        }

        @NonNull
        public Context getContext() {
            return this.builder.getContext();
        }

        public AlertDialog.Builder setTitle(@StringRes int titleId) {
            this.builder.setTitle(titleId);
            return this;
        }

        public AlertDialog.Builder setTitle(CharSequence title) {
            this.builder.setTitle(title);
            return this;
        }

        public AlertDialog.Builder setCustomTitle(View customTitleView) {
            this.builder.setCustomTitle(customTitleView);
            return this;
        }

        public AlertDialog.Builder setMessage(@StringRes int messageId) {
            this.builder.setMessage(messageId);
            return this;
        }

        public AlertDialog.Builder setMessage(CharSequence message) {
            this.builder.setMessage(message);
            return this;
        }

        public AlertDialog.Builder setIcon(@DrawableRes int iconId) {
            this.builder.setIcon(iconId);
            return this;
        }

        public AlertDialog.Builder setIcon(Drawable icon) {
            this.builder.setIcon(icon);
            return this;
        }

        public AlertDialog.Builder setIconAttribute(@AttrRes int attrId) {
            this.builder.setIconAttribute(attrId);
            return this;
        }

        public AlertDialog.Builder setPositiveButton(@StringRes int textId, DialogInterface.OnClickListener listener) {
            this.builder.setPositiveButton(textId, listener);
            return this;
        }

        public AlertDialog.Builder setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
            this.builder.setPositiveButton(text, listener);
            return this;
        }

        public AlertDialog.Builder setNegativeButton(@StringRes int textId, DialogInterface.OnClickListener listener) {
            this.builder.setNegativeButton(textId, listener);
            return this;
        }

        public AlertDialog.Builder setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) {
            this.builder.setNegativeButton(text, listener);
            return this;
        }

        public AlertDialog.Builder setNeutralButton(@StringRes int textId, DialogInterface.OnClickListener listener) {
            this.builder.setNeutralButton(textId, listener);
            return this;
        }

        public AlertDialog.Builder setNeutralButton(CharSequence text, DialogInterface.OnClickListener listener) {
            this.builder.setNeutralButton(text, listener);
            return this;
        }

        public AlertDialog.Builder setCancelable(boolean cancelable) {
            this.builder.setCancelable(cancelable);
            return this;
        }

        public AlertDialog.Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.builder.setOnCancelListener(onCancelListener);
            return this;
        }

        public AlertDialog.Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            if(Build.VERSION.SDK_INT >= 17) {
                this.builder.setOnDismissListener(onDismissListener);
            }

            return this;
        }

        public AlertDialog.Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            this.builder.setOnKeyListener(onKeyListener);
            return this;
        }

        public AlertDialog.Builder setItems(@ArrayRes int itemsId, DialogInterface.OnClickListener listener) {
            this.builder.setItems(itemsId, listener);
            return this;
        }

        public AlertDialog.Builder setItems(CharSequence[] items, DialogInterface.OnClickListener listener) {
            this.builder.setItems(items, listener);
            return this;
        }

        public AlertDialog.Builder setAdapter(ListAdapter adapter, DialogInterface.OnClickListener listener) {
            this.builder.setAdapter(adapter, listener);
            return this;
        }

        public AlertDialog.Builder setCursor(Cursor cursor, DialogInterface.OnClickListener listener, String labelColumn) {
            this.builder.setCursor(cursor, listener, labelColumn);
            return this;
        }

        public AlertDialog.Builder setMultiChoiceItems(@ArrayRes int itemsId, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
            this.builder.setMultiChoiceItems(itemsId, checkedItems, listener);
            return this;
        }

        public AlertDialog.Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
            this.builder.setMultiChoiceItems(items, checkedItems, listener);
            return this;
        }

        public AlertDialog.Builder setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn, DialogInterface.OnMultiChoiceClickListener listener) {
            this.builder.setMultiChoiceItems(cursor, isCheckedColumn, labelColumn, listener);
            return this;
        }

        public AlertDialog.Builder setSingleChoiceItems(@ArrayRes int itemsId, int checkedItem, DialogInterface.OnClickListener listener) {
            this.builder.setSingleChoiceItems(itemsId, checkedItem, listener);
            return this;
        }

        public AlertDialog.Builder setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn, DialogInterface.OnClickListener listener) {
            this.builder.setSingleChoiceItems(cursor, checkedItem, labelColumn, listener);
            return this;
        }

        public AlertDialog.Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, DialogInterface.OnClickListener listener) {
            this.builder.setSingleChoiceItems(items, checkedItem, listener);
            return this;
        }

        public AlertDialog.Builder setSingleChoiceItems(ListAdapter adapter, int checkedItem, DialogInterface.OnClickListener listener) {
            this.builder.setSingleChoiceItems(adapter, checkedItem, listener);
            return this;
        }

        public AlertDialog.Builder setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
            this.builder.setOnItemSelectedListener(listener);
            return this;
        }

        public AlertDialog.Builder setView(int layoutResId) {
            if(Build.VERSION.SDK_INT >= 21) {
                this.builder.setView(layoutResId);
            }

            return this;
        }

        public AlertDialog.Builder setView(View view) {
            this.builder.setView(view);
            return this;
        }

        public AlertDialog create() {
            return new AlertDialog.Api21Dialog(this.builder.create());
        }

        public AlertDialog show() {
            AlertDialog dialog = this.create();
            dialog.show();
            return dialog;
        }
    }

    public interface Builder {
        @NonNull
        Context getContext();

        AlertDialog.Builder setTitle(@StringRes int var1);

        AlertDialog.Builder setTitle(CharSequence var1);

        AlertDialog.Builder setCustomTitle(View var1);

        AlertDialog.Builder setMessage(@StringRes int var1);

        AlertDialog.Builder setMessage(CharSequence var1);

        AlertDialog.Builder setIcon(@DrawableRes int var1);

        AlertDialog.Builder setIcon(Drawable var1);

        AlertDialog.Builder setIconAttribute(@AttrRes int var1);

        AlertDialog.Builder setPositiveButton(@StringRes int var1, DialogInterface.OnClickListener var2);

        AlertDialog.Builder setPositiveButton(CharSequence var1, DialogInterface.OnClickListener var2);

        AlertDialog.Builder setNegativeButton(@StringRes int var1, DialogInterface.OnClickListener var2);

        AlertDialog.Builder setNegativeButton(CharSequence var1, DialogInterface.OnClickListener var2);

        AlertDialog.Builder setNeutralButton(@StringRes int var1, DialogInterface.OnClickListener var2);

        AlertDialog.Builder setNeutralButton(CharSequence var1, DialogInterface.OnClickListener var2);

        AlertDialog.Builder setCancelable(boolean var1);

        AlertDialog.Builder setOnCancelListener(DialogInterface.OnCancelListener var1);

        AlertDialog.Builder setOnDismissListener(DialogInterface.OnDismissListener var1);

        AlertDialog.Builder setOnKeyListener(DialogInterface.OnKeyListener var1);

        AlertDialog.Builder setItems(@ArrayRes int var1, DialogInterface.OnClickListener var2);

        AlertDialog.Builder setItems(CharSequence[] var1, DialogInterface.OnClickListener var2);

        AlertDialog.Builder setAdapter(ListAdapter var1, DialogInterface.OnClickListener var2);

        AlertDialog.Builder setCursor(Cursor var1, DialogInterface.OnClickListener var2, String var3);

        AlertDialog.Builder setMultiChoiceItems(@ArrayRes int var1, boolean[] var2, DialogInterface.OnMultiChoiceClickListener var3);

        AlertDialog.Builder setMultiChoiceItems(CharSequence[] var1, boolean[] var2, DialogInterface.OnMultiChoiceClickListener var3);

        AlertDialog.Builder setMultiChoiceItems(Cursor var1, String var2, String var3, DialogInterface.OnMultiChoiceClickListener var4);

        AlertDialog.Builder setSingleChoiceItems(@ArrayRes int var1, int var2, DialogInterface.OnClickListener var3);

        AlertDialog.Builder setSingleChoiceItems(Cursor var1, int var2, String var3, DialogInterface.OnClickListener var4);

        AlertDialog.Builder setSingleChoiceItems(CharSequence[] var1, int var2, DialogInterface.OnClickListener var3);

        AlertDialog.Builder setSingleChoiceItems(ListAdapter var1, int var2, DialogInterface.OnClickListener var3);

        AlertDialog.Builder setOnItemSelectedListener(AdapterView.OnItemSelectedListener var1);

        AlertDialog.Builder setView(int var1);

        AlertDialog.Builder setView(View var1);

        AlertDialog create();

        AlertDialog show();
    }

    private static class Api20Dialog extends AlertDialog {
        private android.support.v7.app.AlertDialog alertDialog;

        private Api20Dialog(android.support.v7.app.AlertDialog alertDialog) {
            this.alertDialog = alertDialog;
        }

        public void show() {
            this.alertDialog.show();
        }

        public void dismiss() {
            if(this.alertDialog.isShowing()) {
                this.alertDialog.dismiss();
            }

        }

        public boolean isShowing() {
            return this.alertDialog.isShowing();
        }

        public void cancel() {
            if(this.alertDialog.isShowing()) {
                this.alertDialog.cancel();
            }

        }

        public Button getButton(int whichButton) {
            return this.alertDialog.getButton(whichButton);
        }

        @Nullable
        public ListView getListView() {
            return this.alertDialog.getListView();
        }

        @NonNull
        public Context getContext() {
            return this.alertDialog.getContext();
        }

        @Nullable
        public View getCurrentFocus() {
            return this.alertDialog.getCurrentFocus();
        }

        @NonNull
        public LayoutInflater getLayoutInflater() {
            return this.alertDialog.getLayoutInflater();
        }

        @Nullable
        public Activity getOwnerActivity() {
            return this.alertDialog.getOwnerActivity();
        }

        public int getVolumeControlStream() {
            return this.alertDialog.getVolumeControlStream();
        }

        @Nullable
        public Window getWindow() {
            return this.alertDialog.getWindow();
        }
    }

    private static class Api21Dialog extends AlertDialog {
        private android.app.AlertDialog alertDialogApp;

        private Api21Dialog(android.app.AlertDialog alertDialog) {
            this.alertDialogApp = alertDialog;
        }

        public void show() {
            this.alertDialogApp.show();
        }

        public void dismiss() {
            if(this.alertDialogApp.isShowing()) {
                this.alertDialogApp.dismiss();
            }

        }

        public boolean isShowing() {
            return this.alertDialogApp.isShowing();
        }

        public void cancel() {
            if(this.alertDialogApp.isShowing()) {
                this.alertDialogApp.cancel();
            }

        }

        public Button getButton(int whichButton) {
            return this.alertDialogApp.getButton(whichButton);
        }

        @Nullable
        public ListView getListView() {
            return this.alertDialogApp.getListView();
        }

        @NonNull
        public Context getContext() {
            return this.alertDialogApp.getContext();
        }

        @Nullable
        public View getCurrentFocus() {
            return this.alertDialogApp.getCurrentFocus();
        }

        @NonNull
        public LayoutInflater getLayoutInflater() {
            return this.alertDialogApp.getLayoutInflater();
        }

        @Nullable
        public Activity getOwnerActivity() {
            return this.alertDialogApp.getOwnerActivity();
        }

        public int getVolumeControlStream() {
            return this.alertDialogApp.getVolumeControlStream();
        }

        @Nullable
        public Window getWindow() {
            return this.alertDialogApp.getWindow();
        }
    }
}
