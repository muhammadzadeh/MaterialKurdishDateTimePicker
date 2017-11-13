# MaterialKurdishDateTimePicker
An Android library containing a Kurdish date picker and a time picker designed on [Google's Material Design Principals For Pickers](http://www.google.com/design/spec/components/pickers.html) for Android 4.0.3 (API 15) +.

Date Picker | Time Picker
---- | ----
![Date Picker](https://github.com/mohamad-amin/KurdishMaterialDateTimePicker/raw/master/resources/datepicker.png) | ![Time Picker](https://github.com/mohamad-amin/KurdishMaterialDateTimePicker/raw/master/resources/timepicker.png)

You can report any issue on issues page. **Note: If you speak Kurdish, you can submit issues with Kurdish language and I will check them. :)**

#Importing

Add MaterialKurdishDateTimePicker as a dependency to your `build.gradle` file:
```gradle
dependencies {
    compile 'compile 'ir.muhammadzadeh:materialkurdishdatetimepicker:0.1.5''
}
```


# Usage
The library follows the same API as other pickers in the Android framework.
After adding the library, for using a picker in your project you need to:

1. Implement an `OnTimeSetListener`/`OnDateSetListener`
2. Create a `TimePickerDialog`/`DatePickerDialog` using the supplied factory
3. Theme the pickers

### Implement an `OnTimeSetListener`/`OnDateSetListener`
In order to receive the date or time set in the picker, you will need to implement the `OnTimeSetListener` or
`OnDateSetListener` interfaces. Typically this will be the `Activity` or `Fragment` that creates the Pickers. The callbacks use the same API as the standard Android pickers.
```java
@Override
public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
  String time = "You picked the following time: "+hourOfDay+"h"+minute;
  timeTextView.setText(time);
}

@Override
public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
  String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
  dateTextView.setText(date);
}
```

### Create a `TimePickerDialog`/`DatePickerDialog` using the supplied factory
You will need to create a new instance of `TimePickerDialog` or `DatePickerDialog` using the static `newInstance()` method, supplying proper default values and a callback. Once the dialogs are configured, you can call `show()`.
```java
KurdishCalendar kurdishCalendar = new KurdishCalendar();
DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
  MainActivity.this,
  kurdishCalendar.getKurdishYear(),
  kurdishCalendar.getKurdishMonth(),
  kurdishCalendar.getKurdishDay()
);
datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
```

### Theme the pickers
You can theme the pickers by overwriting the color resources `mdtp_accent_color` and `mdtp_accent_color_dark` in your project.
```xml
<color name="mdtp_accent_color">#009688</color>
<color name="mdtp_accent_color_dark">#00796b</color>
```

#Additional Options
* `TimePickerDialog` dark theme  
The `TimePickerDialog` has a dark theme that can be set by calling
```java
timePickerDialog.setThemeDark(true);
```

* `DatePickerDialog` dark theme
The `DatePickerDialog` has a dark theme that can be set by calling
```java
datePickerDialog.setThemeDark(true);
```

* `TimePickerDialog` `setTitle(String title)`
Shows a title at the top of the `TimePickerDialog`

* `setSelectableDays(KurdishCalendar[] days)`
You can pass a `KurdishCalendar[]` to the `DatePickerDialog`. This values in this list are the only acceptable dates for the picker. It takes precedence over `setMinDate(KurdishCalendar day)` and `setMaxDate(KurdishCalendar day)`

* `setHighlightedDays(KurdishCalendar[] days)`
You can pass a `KurdishCalendar[]` of days to highlight. They will be rendered in bold. You can tweak the color of the highlighted days by overwriting `mdtp_date_picker_text_highlighted`

* `OnDismissListener` and `OnCancelListener`  
Both pickers can be passed a `DialogInterface.OnDismissLisener` or `DialogInterface.OnCancelListener` which allows you to run code when either of these events occur.
```java
timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
    @Override
    public void onCancel(DialogInterface dialogInterface) {
      Log.d("TimePicker", "Dialog was cancelled");
    }
});
```

* `vibrate(boolean vibrate)`
Set whether the dialogs should vibrate the device when a selection is made. This defaults to `true`.

#Credits 
This libary is completely based on  [PersianMaterialDateTimePicker](https://github.com/mohamad-amin/PersianMaterialDateTimePicker).

