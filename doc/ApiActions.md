# Actions

| Action | Controller | Description | Request Parameters | Return Parameters |
| -- | -- | -- | -- |
| PING | SystemController | Immediate response | - | - |
| LOAD | SystemController | Get System load | - | 1)System load ( long ) |
| SYSINFO | SystemController | Extract System information ( like number of processors ) | - | 1)Amount of Processors<br>2)Architecture<br>3)Systemname |
| REQCOUNT | SystemController | Returns amount of processed request since last REQCOUNT request<br>Every request ( including PING and LOAD count as requests ) | - | 1)Number of requests |
| GET_CUSTOMERS | OfferController | Returns all customers in system with ID and name | - | 1)ID 1<br>2)Name 1<br>n)ID n<br>n+1)Name n |
| NEW_CUSTOMER | OfferController | Saves the new given customer | 1)Name | 1)ID<br>2)Name |
| GET_OFFERS | OfferController | Returns all offers | - | 1)ID1<br>2)Customer1 name<br>3)Element1 name<br>4)Order1<br>5)... |
| NEW_OFFER | OfferController | Saves the new given offer | 1)Customer Name<br>2)Element Name | 1)ID<br>2)Customer Name<br>3)Element Name<br>4)Order ID |
