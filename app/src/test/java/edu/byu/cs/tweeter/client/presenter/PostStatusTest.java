package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusTest {
    private MainActivityPresenter.View mockView;
    private StatusService mockStatusService;
    private Cache mockCache;
    private MainActivityPresenter mainPresenterSpy;
    private Status newStatus;
    private String post = "post";
    @BeforeEach
    public void setup(){
        mockView = Mockito.mock(MainActivityPresenter.View.class);
        mockStatusService = Mockito.mock(StatusService.class);
        mockCache = Mockito.mock(Cache.class);

        mainPresenterSpy = Mockito.spy(new MainActivityPresenter(mockView));

        Mockito.when(mainPresenterSpy.getStatusService()).thenReturn(mockStatusService);

        Cache.setInstance(mockCache);
        newStatus = new Status(post, mockCache.getCurrUser(),System.currentTimeMillis(), mainPresenterSpy.parseURLs(post), mainPresenterSpy.parseMentions(post));//set the newStatus to be empty



    }


    @Test
    public void testPostStatus_postSuccess(){
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                //error here possibly because the invocation passed in from new status is all empty so error.
                StatusService.PostObserver observer = invocation.getArgument(2, StatusService.PostObserver.class);
                observer.postSuccess("Successfully Posted!");
                return null;
            }
        };
        Mockito.doAnswer(answer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any(), Mockito.any());

        mainPresenterSpy.postStatus(newStatus);
        //verify
        Mockito.verify(mockView).showInfoMessage("Posting Status...");
        Mockito.verify(mockView).hideInfoMessage();
        Mockito.verify(mockView).showInfoMessage("Successfully Posted!");
    }
    @Test
    public void testPostStatus_postFailedWithMessage(){
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                //error here possibly because the invocation passed in from new status is all empty so error.
                StatusService.PostObserver observer = invocation.getArgument(2, StatusService.PostObserver.class);
                observer.handleFailure("Error Message");
                return null;
            }
        };
        Mockito.doAnswer(answer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any(), Mockito.any());

        mainPresenterSpy.postStatus(newStatus);
        //verify
        Mockito.verify(mockView).showInfoMessage("Posting Status...");
        Mockito.verify(mockView).hideInfoMessage();
        Mockito.verify(mockView).showInfoMessage("Failed to post status: Error Message");
    }
    @Test
    public void testPostStatus_postFailedWithException(){
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                //error here possibly because the invocation passed in from new status is all empty so error.
                StatusService.PostObserver observer = invocation.getArgument(2, StatusService.PostObserver.class);
                observer.handleException(new Exception("Exception Message"));
                return null;
            }
        };
        Mockito.doAnswer(answer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any(), Mockito.any());

        mainPresenterSpy.postStatus(newStatus);
        //verify
        Mockito.verify(mockView).showInfoMessage("Posting Status...");
        Mockito.verify(mockView).hideInfoMessage();
        Mockito.verify(mockView).showInfoMessage("Failed to post status because of exception: Exception Message");
    }
}
