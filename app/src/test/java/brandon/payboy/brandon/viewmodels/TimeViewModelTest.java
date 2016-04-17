package brandon.payboy.brandon.viewmodels;

import android.app.Activity;
import android.widget.Chronometer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import brandon.payboy.brandon.ui.fragment.TimeFragment;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimeViewModelTest {
    @Mock
    private TimeFragment fragment;
    @Mock
    private Activity activity;
    @Mock
    private Chronometer chronometer;
    private TimeViewModel viewModel;

    @Before
    public void setup() throws Exception {
        this.viewModel = new TimeViewModel(this.fragment);
        this.viewModel.setup();
    }

    @Test
    public void testSetup() throws Exception {
        assertEquals(this.viewModel.displayTime.get(), "00:00:00");
    }

    @Test
    public void testClearValues() throws Exception {
        when(this.fragment.getChronometer()).thenReturn(this.chronometer);

        this.viewModel.clearValues();

        verify(this.fragment.getChronometer()).stop();
        assertEquals(this.viewModel.displayTime.get(), "00:00:00");
    }

    @Test
    public void testStopCalculating() throws Exception {
        when(this.fragment.getChronometer()).thenReturn(this.chronometer);

        this.viewModel.stopCalculating();

        verify(this.fragment.getChronometer()).stop();
    }

    @Test
    public void testStartCalculating() throws Exception {
        when(this.fragment.getChronometer()).thenReturn(this.chronometer);
        when(this.fragment.getChronometer().getText()).thenReturn("00:00:00");
        when(this.fragment.getChronometer().getBase()).thenReturn(0L);
        when(this.fragment.getElapsedRealTime()).thenReturn(3000L);
        when(this.fragment.getActivity()).thenReturn(this.activity);

        this.viewModel.startCalculating();
        this.viewModel.updateDisplayTime(3000);

        assertEquals(this.viewModel.displayTime.get(), "00:00:03");
    }

    @Test
    public void testResumeCalculating() throws Exception {
        when(this.fragment.getChronometer()).thenReturn(this.chronometer);
        when(this.fragment.getChronometer().getText()).thenReturn("14:40:01");
        when(this.fragment.getChronometer().getBase()).thenReturn(0L);
        when(this.fragment.getElapsedRealTime()).thenReturn(3000L);
        when(this.fragment.getActivity()).thenReturn(this.activity);

        this.viewModel.startCalculating();
        this.viewModel.updateDisplayTime(this.getHour() * 14 + this.getMinute() * 40 + this.getSecond() * 4);

        assertEquals(this.viewModel.displayTime.get(), "14:40:04");
    }

    @Test
    public void testShouldDisplayHour() throws Exception {
        long time = this.getHour();
        this.viewModel.updateDisplayTime(time);

        assertEquals(this.viewModel.displayTime.get(), "01:00:00");
    }

    @Test
    public void testShouldDisplayMinute() throws Exception {
        long time = this.getMinute();
        this.viewModel.updateDisplayTime(time);

        assertEquals(this.viewModel.displayTime.get(), "00:01:00");
    }

    @Test
    public void testShouldDisplayHalfMinute() throws Exception {
        long time = getMinute() / 2;
        this.viewModel.updateDisplayTime(time);

        assertEquals(this.viewModel.displayTime.get(), "00:00:30");
    }

    @Test
    public void testShouldDisplayComplexTime() throws Exception {
        long time = getHour() + (long)(getMinute() * 0.75);
        this.viewModel.updateDisplayTime(time);

        assertEquals(this.viewModel.displayTime.get(), "01:00:45");
    }

    public long getSecond() {
        return 1000;
    }

    public long getMinute() {
        return getSecond() * 60;
    }

    public long getHour() {
        return getMinute() * 60;
    }
}