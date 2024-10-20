package com.ticketing.review.application.service;

import com.ticketing.review.application.dto.event.ReviewDeleteErrorEvent;
import com.ticketing.review.infrastructure.messaging.EventSerializer;
import com.ticketing.review.infrastructure.messaging.ReviewTopic;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void publishReviewDeleteErrorEvent(UUID performanceId, Long userId) {
    kafkaTemplate.send(ReviewTopic.DELETE_ERROR.getTopic(),
        EventSerializer.serialize(new ReviewDeleteErrorEvent(performanceId, userId)));
  }

}
