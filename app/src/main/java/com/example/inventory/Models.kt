<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="#1E1E1E"
    android:orientation="vertical"
    android:padding="16dp">

    <Spinner
        android:id="@+id/transactionItemSpinner"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <EditText
        android:id="@+id/transactionDateInput"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="12dp"
        android:hint="Date (YYYY-MM-DD)"
        android:textColor="#FFFFFF"
        android:textColorHint="#AAAAAA" />

    <Spinner
        android:id="@+id/transactionTypeSpinner"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="12dp" />

    <EditText
        android:id="@+id/transactionQtyInput"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="12dp"
        android:hint="Quantity"
        android:inputType="number"
        android:textColor="#FFFFFF"
        android:textColorHint="#AAAAAA" />

    <EditText
        android:id="@+id/recipientInput"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="12dp"
        android:hint="Issued To / Department"
        android:textColor="#FFFFFF"
        android:textColorHint="#AAAAAA" />

    <EditText
        android:id="@+id/refInput"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="12dp"
        android:hint="Voucher / Ref No"
        android:textColor="#FFFFFF"
        android:textColorHint="#AAAAAA" />

    <EditText
        android:id="@+id/remarksInput"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="12dp"
        android:hint="Remarks"
        android:textColor="#FFFFFF"
        android:textColorHint="#AAAAAA" />
</LinearLayout>
