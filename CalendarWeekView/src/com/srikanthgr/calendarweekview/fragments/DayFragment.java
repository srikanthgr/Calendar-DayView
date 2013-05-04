package com.srikanthgr.calendarweekview.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.srikanthgr.calendarweekview.R;
import com.srikanthgr.calendarweekview.datainterface.DataInterface;
import com.srikanthgr.calendarweekview.model.Event;

/**
 * @author Srikanth G.R
 * 
 */
public class DayFragment extends Fragment {
	private ImageView prevMonth;
	private ImageView nextMonth;
	private TextView currentDate;
	private Calendar _calendar;
	private int month;
	private int yearSelected;
	private int day;
	private Calendar cal;
	private String startDate;
	private Context mContext;

	int margin = 0;
	RelativeLayout monRelative;
	Animation animFlipInForeward;
	Animation animFlipOutForeward;
	Animation animFlipInBackward;
	Animation animFlipOutBackward;
	private String[] timevalues = { "00:00 AM", "00:30 AM", "01:00 AM",
			"01:30 AM", "02:00 AM", "02:30 AM", "03:00 AM", "03:30 AM",
			"04:00 AM", "04:30 AM", "05:00 AM", "05:30 AM", "06:00 AM",
			"06:30 AM", "07:00 AM", "07:30 AM", "08:00 AM", "08:30 AM",
			"09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM", "11:00 AM",
			"11:30 AM", "12:00 PM", "12:30 PM", "13:00 PM", "13:30 PM",
			"14:00 PM", "14:30 PM", "15:00 PM", "15:30 PM", "16:00 PM",
			"16:30 PM", "17:00 PM", "17:30 PM", "18:00 PM", "18:30 PM",
			"19:00 PM", "19:30 PM", "20:00 PM", "20:30 PM", "21:00 PM",
			"21:30 PM", "22:00 PM", "22:30 PM", "23:00 PM", "23:30 PM" };
	private final DateFormat dateformatter = new DateFormat();
	private static final String dateformat = "yyyy/MM/dd";
	private String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
			"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	private String monthsNumbers[] = { "01", "02", "03", "04", "05", "06",
			"07", "08", "09", "10", "11", "12" };
	String eventdate;
	String title;

	public DayFragment() {

	}

	public DayFragment(String startDate) {
		this.startDate = startDate;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup containerView = (ViewGroup) inflater.inflate(
				R.layout.dayview, container, false);
		monRelative = (RelativeLayout) containerView
				.findViewById(R.id.monRelative);
		prevMonth = (ImageView) containerView.findViewById(R.id.prevDay);
		nextMonth = (ImageView) containerView.findViewById(R.id.nextDay);
		currentDate = (TextView) containerView.findViewById(R.id.currentDate);
		_calendar = Calendar.getInstance(Locale.getDefault());
		cal = Calendar.getInstance(Locale.getDefault());
		mContext = getActivity().getApplicationContext();

		cal = Calendar.getInstance(Locale.getDefault());
		month = _calendar.get(Calendar.MONTH);
		yearSelected = _calendar.get(Calendar.YEAR);
		day = _calendar.get(Calendar.DAY_OF_MONTH);
		if (startDate == null) {
			// startDate = yearSelected + "/" + (month + 1) + "/" + day;
			startDate = (month + 1) + "/" + day + "/" + yearSelected;
		}
		Date date = new Date(startDate);
		startDate = (String) dateformatter.format(dateformat, date);
		eventdate = (String) dateformatter.format("MM/dd/yyyy", date);

		date = new Date(startDate);
		cal.setTime(date);

		String month = months[cal.get(Calendar.MONTH)];
		String date1 = " " + cal.get(Calendar.DATE) + " ";

		String year = "" + cal.get(Calendar.YEAR);

		String currentDate1 = date1 + month + "," + year;
		currentDate.setText(currentDate1);
		try {
			loadDataForDay();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// scrollView.setOnTouchListener(gestureListener);
		prevMonth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				try {
					onLTRFling();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});

		nextMonth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					onRTLFling();
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}
		});

		return containerView;
	}

	/**
	 * @throws ParseException
	 */
	private void loadDataForDay() throws ParseException {
		// TODO Auto-generated method stub
		DataInterface dataInterface = new DataInterface(mContext);
		ArrayList<Event> appointments = dataInterface
				.getCurrentDayEvents(eventdate);

		for (Event appointment : appointments) {
			String startTime = appointment.getDtstart();
			String endTime = appointment.getDtend();
			title = appointment.getTitle();

			for (int i = 0; i < timevalues.length; i++) {

				if ((startTime.contains(timevalues[i]))) {
					createViewForAppointment(startTime, endTime);

				}
			}

		}

	}

	/**
	 * @param startTime
	 * @param endTime
	 */
	private void createViewForAppointment(String startTime, String endTime) {
		// TODO Auto-generated method stub
		int marginTop = calculateMargin(startTime);
		int height = (int) calculateDiffInTime(startTime, endTime);

		LayoutParams lprams = new LayoutParams(LayoutParams.MATCH_PARENT,
				height);
		int marginLeft = 30;
		lprams.setMargins(marginLeft, 0, 0, 0);
		lprams.topMargin = marginTop;

		Button button = new Button(mContext);
		button.setBackgroundResource(R.drawable.appointment_new);
		button.setLayoutParams(lprams);
		button.setTextColor(Color.BLACK);
		button.setTextAppearance(mContext, R.style.ButtonFontStyle);
		button.setText("Event");
		if (height <= 18) {
			button.setSingleLine();
		}
		button.setEllipsize(TruncateAt.END);
		monRelative.addView(button);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Toast.makeText(mContext, "Button Click1", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	/**
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private long calculateDiffInTime(String startTime, String endTime) {
		// TODO Auto-generated method stub
		String startTimeAppointment = startTime;
		String endTimeAppointment = endTime;
		long diffMinutes = 0;
		SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(startTimeAppointment);
			d2 = format.parse(endTimeAppointment);

			long diff = d2.getTime() - d1.getTime();
			diffMinutes = diff / (60 * 1000);
			float diffHours = diffMinutes / 60;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diffMinutes;
	}

	/**
	 * @param startTime
	 * @return
	 */
	private int calculateMargin(String startTime) {
		// TODO Auto-generated method stub
		margin = 3;
		for (int i = 0; startTime.compareToIgnoreCase(timevalues[i]) != 0; i++) {
			margin = margin + 30;
		}

		return margin;
	}

	/**
	 * @param month
	 * @param year
	 * @throws ParseException
	 */
	private void setGridCellAdapterToDate(int month, int year)
			throws ParseException {
		Calendar date = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		String monthTemp = months[cal.get(Calendar.MONTH)];
		String newdate = " " + cal.get(Calendar.DATE) + " ";
		String dateTime = sdf.format(date.getTime());
		String yearTemp = "" + cal.get(Calendar.YEAR);
		String monthTemp1 = monthsNumbers[cal.get(Calendar.MONTH)];

		int changedmonth = cal.get(Calendar.MONTH);
		int changedYear = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String changedNewDate = monthTemp1 + "/" + day + "/" + yearSelected;
		eventdate = changedNewDate;
		loadDataForDay();
		String changedDate = newdate + monthTemp + "," + yearTemp;

		currentDate.setText(changedDate);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	private void onLTRFling() throws ParseException {
		removeViews();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		setGridCellAdapterToDate(cal.MONTH, cal.YEAR);

	}

	private void onRTLFling() throws ParseException {
		removeViews();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		setGridCellAdapterToDate(cal.MONTH, cal.YEAR);

	}

	private void removeViews() {
		monRelative.removeAllViews();

	}
}