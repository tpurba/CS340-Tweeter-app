package edu.byu.cs.tweeter.client.presenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusTest {
    private MainActivityPresenter.View mockView;
    private StatusService mockStatusService;
    private Cache mockCache;
    private MainActivityPresenter mainPresenterSpy;
    private Status newStatus;
    private AuthToken mockAuthToken;
    private String post = "post";
    @BeforeEach
    public void setup(){
        mockView = Mockito.mock(MainActivityPresenter.View.class);
        mockStatusService = Mockito.mock(StatusService.class);
        mockCache = Mockito.mock(Cache.class);
        mockAuthToken = Mockito.mock(AuthToken.class);

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
                //All parameters passed by the Presenter to the Service's "post status" operation are correct (Right type and right value).
                //assert check for auth token and status
                AuthToken authToken = invocation.getArgument(0, AuthToken.class);
                Status status = invocation.getArgument(1, Status.class);
                StatusService.PostObserver observer = invocation.getArgument(2, StatusService.PostObserver.class);
                //check if right type
                assertNotNull(authToken);
                assertNotNull(status);
                assertEquals(newStatus, status);
                // Check if authToken is of the expected type
                if (!(authToken instanceof AuthToken)) {
                    // Handle the case where the type is not as expected
                    throw new AssertionError("AuthToken has unexpected type");
                }
                // Check if status is of the expected type
                if (!(status instanceof Status)) {
                    // Handle the case where the type is not as expected
                    throw new AssertionError("Status has unexpected type");
                }
                observer.postSuccess("Successfully Posted!");
                return null;
            }
        };
        Mockito.when(mockCache.getCurrUserAuthToken()).thenReturn(mockAuthToken);//when invocation is called to get the authtoken it mocks it and gets a authtoken
        Mockito.doAnswer(answer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any(), Mockito.any());//AuthToken currAuthToken, Status newStatus, PostObserver observer

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
                AuthToken authToken = invocation.getArgument(0, AuthToken.class);
                Status status = invocation.getArgument(1, Status.class);
                StatusService.PostObserver observer = invocation.getArgument(2, StatusService.PostObserver.class);
                //check if right type
                assertNotNull(authToken);
                assertNotNull(status);
                assertEquals(newStatus, status);
                // Check if authToken is of the expected type
                if (!(authToken instanceof AuthToken)) {
                    // Handle the case where the type is not as expected
                    throw new AssertionError("AuthToken has unexpected type");
                }
                // Check if status is of the expected type
                if (!(status instanceof Status)) {
                    // Handle the case where the type is not as expected
                    throw new AssertionError("Status has unexpected type");
                }
                observer.handleFailure("Error Message");
                return null;
            }
        };
        Mockito.when(mockCache.getCurrUserAuthToken()).thenReturn(mockAuthToken);//when invocation is called to get the authtoken it mocks it and gets a authtoken
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
                AuthToken authToken = invocation.getArgument(0, AuthToken.class);
                Status status = invocation.getArgument(1, Status.class);
                StatusService.PostObserver observer = invocation.getArgument(2, StatusService.PostObserver.class);
                //check if right type
                assertNotNull(authToken);
                assertNotNull(status);
                assertEquals(newStatus, status);
                // Check if authToken is of the expected type
                if (!(authToken instanceof AuthToken)) {
                    // Handle the case where the type is not as expected
                    throw new AssertionError("AuthToken has unexpected type");
                }
                // Check if status is of the expected type
                if (!(status instanceof Status)) {
                    // Handle the case where the type is not as expected
                    throw new AssertionError("Status has unexpected type");
                }
                observer.handleException(new Exception("Exception Message"));
                return null;
            }
        };
        Mockito.when(mockCache.getCurrUserAuthToken()).thenReturn(mockAuthToken);//when invocation is called to get the authtoken it mocks it and gets a authtoken 
        Mockito.doAnswer(answer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any(), Mockito.any());

        mainPresenterSpy.postStatus(newStatus);
        //verify
        Mockito.verify(mockView).showInfoMessage("Posting Status...");
        Mockito.verify(mockView).hideInfoMessage();
        Mockito.verify(mockView).showInfoMessage("Failed to post status because of exception: Exception Message");
    }
}
