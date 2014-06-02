MPS Server Protocol
===================

The MPS application communicates via a TCP API with the Dispatcher application. The protocol is a simple byte stream. The order of the information is strict.

Request Format
--------------

A client request basicly consist of 3 parts. The first part is the action name. The MPS Server provides a given list of actions it can handle. An action can be ADD_ELEMENT which creates a new element in the system based on the given parameters ( Parameters are also part of the request and described later ), or an action can be PING which triggers a simple response from the system ( heartbeat ).
The second part of the request is the unique user identification. This identifications allows the dispatcher to identify which response is for which user. The server will never interpret this string but simply integrate it in the response. Thus if you are communicating directly with the system this part might also be filled with zeros. But be aware that it can not be ignored.
The third part are the parameters. This is list of Strings. Every action has a given list of needed parameters. Check the action documentation for details on how many parameters you have to add for a specific request.

### Byte stream details

1. length of the action name ( 32 bit unsigned integer )
2. action name ( length determined by 1. )
3. user identification ( 32 bit unsigned integer )
4. amaount of paramerters to read ( 32 bit unsigned integer )
5. length of the first parameter ( 32 bit unsigned integer )
6. first parameter
7. ...

Response Format
---------------

ToDo Desciption text

### Byte stream details

1. Response Code ( 32 bit unsigned integer )
2. length of response action ( 32 bit unsigned integer )
3. response action ( length determined by 2. )
4. user identification ( 32 bit unsigned integer )
5. amount of parameters to read ( 32 bit unsigned integer )
6. length of the first parameter ( 32 bit unsigned integer )
7. first parameter
8. ...
