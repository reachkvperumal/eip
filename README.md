# eip
demo of enterprise integration patterns 

### Alert producer notifies broadcast service (Enterprise Integration Pattern)
### Broadcast service notifies all subscribers 
### In this example I got 2 consumers

```
 Inbound Gateway waits for message from producer
 Then it notifies the consumer using CachedThreadPool (Strictly for demo purpose)

```
![Demo.png](..%2F..%2FDesktop%2FDemo.png)