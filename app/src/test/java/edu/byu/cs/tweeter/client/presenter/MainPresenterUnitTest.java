package edu.byu.cs.tweeter.client.presenter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;

public class MainPresenterUnitTest {
    private MainActivityPresenter.View mockView;
    private UserService mockUserService;
    private Cache mockCache;
    private MainActivityPresenter mainPresenterSpy;

    @Before
    public void setup(){
        //create mocks
        mockView = Mockito.mock(MainActivityPresenter.View.class);
        mockUserService = Mockito.mock(UserService.class);
        mockCache = Mockito.mock(Cache.class);
        //set spy
        mainPresenterSpy = Mockito.spy(new MainActivityPresenter(mockView));

        //everytime the getuserservice call happense it will return the mockuserservice(will always work even with NULL) no type chekcing
        //Mockito.doReturn(mockUserService).when(mainPresenterSpy).getUserService();
        //get type checking of what is returned to what we expect recommended way (assumes it returns something ) doesnt work with NULL
        Mockito.when(mainPresenterSpy.getUserService()).thenReturn(mockUserService);

        Cache.setInstance(mockCache);

    }

    @Test
    public void testLogout_logoutSuccessful(){
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                UserService.LogoutObserver observer = invocation.getArgument(0, UserService.LogoutObserver.class);
                observer.logOutSuccess();
                return null;
            }
        };
        Mockito.doAnswer(answer).when(mockUserService).logOut(Mockito.any(),Mockito.any());
        mainPresenterSpy.logOut();
        Mockito.verify(mockView).showInfoMessage("Logging Out...");
        Mockito.verify(mockCache).clearCache();
        Mockito.verify(mockView).hideInfoMessage();
        Mockito.verify(mockView).logoutUser();

    }
    @Test
    public void testLogout_logoutFailedWithMessage(){
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                UserService.LogoutObserver observer = invocation.getArgument(0, UserService.LogoutObserver.class);
                observer.handleFailure("the error message");
                return null;
            }
        };

        Mockito.doAnswer(answer).when(mockUserService).logOut(Mockito.any(),Mockito.any());
        mainPresenterSpy.logOut();

        Mockito.verify(mockView).showInfoMessage("Logging Out...");

        Mockito.verify(mockCache, Mockito.times(0)).clearCache();
        Mockito.verify(mockView).hideInfoMessage();
        Mockito.verify(mockView).showInfoMessage("Failed to logout: the error message" );


    }
    @Test
    public void testLogout_logoutFailedWithException(){
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                UserService.LogoutObserver observer = invocation.getArgument(0, UserService.LogoutObserver.class);
                observer.handleException(new Exception("the exception message"));
                return null;
            }
        };

        Mockito.doAnswer(answer).when(mockUserService).logOut(Mockito.any(),Mockito.any());
        mainPresenterSpy.logOut();

        Mockito.verify(mockView).showInfoMessage("Logging Out...");

        Mockito.verify(mockCache, Mockito.times(0)).clearCache();
        Mockito.verify(mockView).hideInfoMessage();
        Mockito.verify(mockView).showInfoMessage("Failed to logout because of exception: the exception message" );
    }
}

