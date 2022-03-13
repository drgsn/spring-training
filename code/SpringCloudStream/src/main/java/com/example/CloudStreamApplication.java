package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudStreamApplication {

	/**
	 * Example using
	 *  - Source: is the application that consumes events
	 *  - Sink: either consumes from a Source or Processor
	 *
	 *	An alternative to this would be to use the @Output and @Input annotations.
	 * This is done by declaring a interface with a method annotated with either @Output or @Input depending on the case.
	 * EG:
	 * // Producer
	 * interface PublisherChannel {
	 * 		@Output
	 *     	MessageChannel producer();
	 * }
	 * // to use annotate class with @EnableBiding(PublisherChannel.class) and
	 * 		@Autowire
	 * 		PublisherChannel channel;
	 * 	// to send message
	 * 	channel.producer().send(message);
	 *
	 * // Consumer
	 * interface ConsumerChannel {
	 * 		@Input
	 * 		SubscriableChannel consumer();
	 * }
	 * // to use the consumer annotate class with @EnableBinding(ConsumerChannel.class)
	 * 		@Autowire
	 * 		ConsumerChannel consumer;
	 *
	 * 		@StreamListener(
	 *   	target = MyProcessor.INPUT,
	 *   	condition = "payload < 10")
	 * 		public void consumeMessage(Integer val) {
	 *     		// ...
	 * 		}
	 *
	 * 	keep in mind that this means read the message from the processor do some processing and send it forward to sink
	 * @StreamListener(Processor.INPUT)
	 * @SendTo(Processor.OUTPUT)
	 * public LogMessage enrichLogMessage(LogMessage log) {
 	 * 		return new LogMessage(String.format("[1]: %s", log.getMessage()));
 	 * }
	 */


	public static void main(String[] args) {
		SpringApplication.run(CloudStreamApplication.class, args);
	}

}
