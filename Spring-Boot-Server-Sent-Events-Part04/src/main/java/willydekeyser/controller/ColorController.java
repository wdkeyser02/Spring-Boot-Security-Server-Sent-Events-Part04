package willydekeyser.controller;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class ColorController {

	private final ExecutorService executor = Executors.newCachedThreadPool();
	private Random rand = new Random();
	
	@GetMapping("/time")
	public SseEmitter time() {
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		executor.execute(() -> {
            try {
            	Thread.sleep(5000);
                while (true) {
                	emitter.send(SseEmitter.event()
                			.name("red")
                			.data("" + rand.nextInt(255))
                			.build());
                	emitter.send(SseEmitter.event()
                			.name("green")
                			.data("" + rand.nextInt(255))
                			.build());
                	emitter.send(SseEmitter.event()
                			.name("blue")
                			.data("" + rand.nextInt(255))
                			.build());
                    Thread.sleep(1000);
                }
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            }
		});  
		return emitter;
	}
	
}