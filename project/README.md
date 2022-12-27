# Design Choices

I used the book example from the labs to setup the initial client/server connection and the message passing method.

As the bugList and users list are shared between clients their methods use the synchronized keyword to prevent inconsistent data.

For the bugIDs I used an AtomicInteger as a counter to avoid multiple bugs having the same ID while keeping the IDs uniform and simple.

I used enums for the bug status and bug platform for type safety, consistency and error prevention


# How it works

When the server is started it listens for a connection on port 2004. When the client connects the server starts a new thread. The client and the thread communicate and the client can read and write to the shared lists.