package brandon.payboy.brandon.viewmodels;

import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Locale;

import brandon.payboy.brandon.ui.fragment.MoneyFragment;
import brandon.payboy.brandon.util.AppPreferences;
import brandon.payboy.brandon.util.Notification;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MoneyViewModelTest {
    @Mock
    private MoneyFragment fragment;
    private MoneyViewModel viewModel;
    @Mock
    private AppPreferences appPreferences;
    @Mock
    private Notification notification;

    @Before
    public void setup() throws Exception {
        this.viewModel = new MoneyViewModel(fragment, appPreferences, notification);
    }

    @Test
    public void testSetup() throws Exception {
        when(appPreferences.getWageValue()).thenReturn(20.0);
        when(appPreferences.isNotificationEnabled()).thenReturn(true);

        viewModel.setup();

        Assert.assertEquals(viewModel.displayMoney.get(), "0.00");
    }

    @Test
    public void testClear() throws Exception {
        viewModel.clearValues();

        assertEquals(viewModel.displayMoney.get(), "0.00");
        verify(notification).cancel();
    }

    @Test
    public void testSetWageValueWithNotificationEnabled() throws Exception {
        when(appPreferences.getWageValue()).thenReturn(100.0);
        when(appPreferences.isNotificationEnabled()).thenReturn(true);

        viewModel.setup();
        long seconds = 30;
        viewModel.setMoneyValue(seconds);

        String expectedDisplayValue = calculationHelper(seconds);
        assertEquals(viewModel.displayMoney.get(), expectedDisplayValue);
        verify(notification).update(expectedDisplayValue);
    }

    @Test
    public void testSetWageValueWithNotificationDisabled() throws Exception {
        when(appPreferences.getWageValue()).thenReturn(20.0);
        when(appPreferences.isNotificationEnabled()).thenReturn(false);

        viewModel.setup();
        long seconds = 100;
        viewModel.setMoneyValue(seconds);

        String expectedDisplayValue = calculationHelper(seconds);
        assertEquals(viewModel.displayMoney.get(), expectedDisplayValue);
        verify(notification, never()).update(expectedDisplayValue);
    }

    public String calculationHelper(long seconds) {
        double ratePerSecond = appPreferences.getWageValue() / 3600.0;
        double moneyValue = ratePerSecond * seconds;
        return String.format(Locale.US, "%.2f", moneyValue);
    }

    @Test
    public void testSettingsPressed() throws Exception {
        viewModel.settingsPressed(new View(fragment.getActivity()));
        verify(fragment).settingsPressed();
    }
}