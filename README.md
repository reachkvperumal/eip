# eip
demo of enterprise integration patterns 

### Alert producer notifies broadcast service (Enterprise Integration Pattern)
### Broadcast service notifies all subscribers 
### In this example I got 2 consumers

```
 Inbound Gateway waits for message from producer
 Then it notifies the consumer using CachedThreadPool (Strictly for demo purpose)

```

![Screenshot 2024-01-07 at 2.05.57 AM.png](..%2F..%2FDesktop%2FScreenshot%202024-01-07%20at%202.05.57%E2%80%AFAM.png)