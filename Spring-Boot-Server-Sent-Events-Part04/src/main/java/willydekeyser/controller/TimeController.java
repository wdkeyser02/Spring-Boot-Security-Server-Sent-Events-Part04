package willydekeyser.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class TimeController {

	private final ExecutorService executor = Executors.newCachedThreadPool();
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
	
	@GetMapping("/time")
	public SseEmitter time() {
		
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		executor.execute(() -> {
            try {
                while (true) {
                	emitter.send(SseEmitter.event()
                			.name("test")
                			.data("Date: " + LocalDateTime.now().format(formatter))
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