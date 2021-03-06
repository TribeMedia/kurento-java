package org.kurento.test.functional.dispatcheronetomany;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;
import org.kurento.client.DispatcherOneToMany;
import org.kurento.client.HubPort;
import org.kurento.client.MediaPipeline;
import org.kurento.client.WebRtcEndpoint;
import org.kurento.test.base.FunctionalTest;
import org.kurento.test.client.BrowserClient;
import org.kurento.test.client.BrowserType;
import org.kurento.test.client.Client;
import org.kurento.test.client.WebRtcChannel;
import org.kurento.test.client.WebRtcMode;
import org.kurento.test.config.BrowserScope;
import org.kurento.test.config.TestScenario;

/**
 * 
 * <strong>Description</strong>: A WebRtcEndpoint is connected to another
 * WebRtcEndpoint through a Dispatcher.<br/>
 * <strong>Pipeline</strong>:
 * <ul>
 * <li>WebRtcEndpoint -> Dispatcher -> WebRtcEndpoint</li>
 * </ul>
 * <strong>Pass criteria</strong>:
 * <ul>
 * <li>Media should be received in the video tag</li>
 * <li>Color of the video should be the expected</li>
 * </ul>
 * 
 * @author Boni Garcia (bgarcia@gsyc.es)
 * @author David Fernandez (d.fernandezlop@gmail.com)
 * @since 6.0.0
 */

public class DispatcherOneToManyWebRtcTest extends FunctionalTest {
	private static final int PLAYTIME = 10; // seconds
	private static final String BROWSER1 = "browser1";
	private static final String BROWSER2 = "browser2";
	private static final String BROWSER3 = "browser3";

	public DispatcherOneToManyWebRtcTest(TestScenario testScenario) {
		super(testScenario);
	}

	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		TestScenario test = new TestScenario();

		test.addBrowser(
				BROWSER1,
				new BrowserClient.Builder().browserType(BrowserType.CHROME)
						.client(Client.WEBRTC).scope(BrowserScope.LOCAL)
						.video(getPathTestFiles() + "/video/10sec/green.y4m")
						.build());
		test.addBrowser(
				BROWSER2,
				new BrowserClient.Builder().browserType(BrowserType.CHROME)
						.client(Client.WEBRTC).scope(BrowserScope.LOCAL)
						.video(getPathTestFiles() + "/video/10sec/blue.y4m")
						.build());
		test.addBrowser(
				BROWSER3,
				new BrowserClient.Builder().browserType(BrowserType.CHROME)
						.client(Client.WEBRTC).scope(BrowserScope.LOCAL)
						.video(getPathTestFiles() + "/video/10sec/red.y4m")
						.build());

		return Arrays.asList(new Object[][] { { test } });
	}

	@Test
	public void testDispatcherOneToManyWebRtc() throws Exception {

		MediaPipeline mp = kurentoClient.createMediaPipeline();
		WebRtcEndpoint webRtcEP1 = new WebRtcEndpoint.Builder(mp).build();
		WebRtcEndpoint webRtcEP2 = new WebRtcEndpoint.Builder(mp).build();
		WebRtcEndpoint webRtcEP3 = new WebRtcEndpoint.Builder(mp).build();

		DispatcherOneToMany dispatcherOneToMany = new DispatcherOneToMany.Builder(
				mp).build();
		HubPort hubPort1 = new HubPort.Builder(dispatcherOneToMany).build();
		HubPort hubPort2 = new HubPort.Builder(dispatcherOneToMany).build();
		HubPort hubPort3 = new HubPort.Builder(dispatcherOneToMany).build();

		webRtcEP1.connect(hubPort1);
		webRtcEP2.connect(hubPort2);
		webRtcEP3.connect(hubPort3);
		hubPort1.connect(webRtcEP1);
		hubPort2.connect(webRtcEP2);
		hubPort3.connect(webRtcEP3);

		dispatcherOneToMany.setSource(hubPort1);

		getBrowser(BROWSER1).subscribeEvents("playing");
		getBrowser(BROWSER1).initWebRtc(webRtcEP1,
				WebRtcChannel.AUDIO_AND_VIDEO, WebRtcMode.SEND_RCV);

		getBrowser(BROWSER2).subscribeEvents("playing");
		getBrowser(BROWSER2).initWebRtc(webRtcEP2,
				WebRtcChannel.AUDIO_AND_VIDEO, WebRtcMode.SEND_RCV);

		getBrowser(BROWSER3).subscribeEvents("playing");
		getBrowser(BROWSER3).initWebRtc(webRtcEP3,
				WebRtcChannel.AUDIO_AND_VIDEO, WebRtcMode.SEND_RCV);

		Thread.sleep(PLAYTIME * 1000);

		// Assertions
		Assert.assertTrue("Not received media (timeout waiting playing event)",
				getBrowser(BROWSER1).waitForEvent("playing"));
		Assert.assertTrue("Not received media (timeout waiting playing event)",
				getBrowser(BROWSER2).waitForEvent("playing"));
		Assert.assertTrue("Not received media (timeout waiting playing event)",
				getBrowser(BROWSER3).waitForEvent("playing"));

		Assert.assertTrue("The color of the video should be green (GREEN)",
				getBrowser(BROWSER1).similarColor(Color.GREEN));
		Assert.assertTrue("The color of the video should be green (GREEN)",
				getBrowser(BROWSER2).similarColor(Color.GREEN));
		Assert.assertTrue("The color of the video should be green (GREEN)",
				getBrowser(BROWSER3).similarColor(Color.GREEN));

		Thread.sleep(3000);
		dispatcherOneToMany.setSource(hubPort2);

		Assert.assertTrue("The color of the video should be blue (BLUE)",
				getBrowser(BROWSER1).similarColor(Color.BLUE));
		Assert.assertTrue("The color of the video should be blue (BLUE)",
				getBrowser(BROWSER2).similarColor(Color.BLUE));
		Assert.assertTrue("The color of the video should be blue (BLUE)",
				getBrowser(BROWSER3).similarColor(Color.BLUE));

		Thread.sleep(3000);
		dispatcherOneToMany.setSource(hubPort3);

		Assert.assertTrue("The color of the video should be red (RED)",
				getBrowser(BROWSER1).similarColor(Color.RED));
		Assert.assertTrue("The color of the video should be red (RED)",
				getBrowser(BROWSER2).similarColor(Color.RED));
		Assert.assertTrue("The color of the video should be red (RED)",
				getBrowser(BROWSER3).similarColor(Color.RED));

		Thread.sleep(3000);
		dispatcherOneToMany.removeSource();
		Assert.assertTrue("The color of the video should be red (RED)",
				getBrowser(BROWSER1).similarColor(Color.RED));
		Assert.assertTrue("The color of the video should be red (RED)",
				getBrowser(BROWSER2).similarColor(Color.RED));
		Assert.assertTrue("The color of the video should be red (RED)",
				getBrowser(BROWSER3).similarColor(Color.RED));

		Thread.sleep(2000);
	}
}
