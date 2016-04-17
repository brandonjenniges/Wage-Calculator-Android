package brandon.payboy.brandon.viewmodels;

import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import brandon.payboy.brandon.ui.fragment.ButtonFragment;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ButtonViewModelTest {
    @Mock
    private ButtonFragment fragment;
    private ButtonViewModel viewModel;

    @Before
    public void setup() throws Exception {
        viewModel = new ButtonViewModel(fragment);
    }

    @Test
    public void testPlayPressed() throws Exception {
        viewModel.playPressed(new View(fragment.getActivity()));
        verify(fragment).playPressed();
    }

    @Test
    public void testPausePressed() throws Exception {
        viewModel.pausePressed(new View(fragment.getActivity()));
        verify(fragment).pausePressed();
    }

    @Test
    public void testResetPressed() throws Exception {
        viewModel.resetPressed(new View(fragment.getActivity()));
        verify(fragment).resetPressed();
    }
}