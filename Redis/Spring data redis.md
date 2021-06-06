### Spring data redis hierarchy

The template offers a high-level abstraction for Redis interactions. While `RedisConnection` offers low-level methods that accept and return binary values (`byte` arrays), the template takes care of serialization and connection management, freeing the user from dealing with such details.

The template is, in fact, the central class of the Redis module, due to its rich feature set.