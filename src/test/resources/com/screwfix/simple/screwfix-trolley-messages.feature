Feature: Screfix trolley messages
Scenario Outline: user add some products to trolley

Given an open browser with home page
And   setup branch
When  click quick shop link
And   enter a productId <productId> in input field
And   enter a productQuantity <qty> in input field
Then  Delivery message on 1 position of product should be '<delivery message>'
And   Collection message on 1 position of product should be '<collection message>'
When  Move to CPC if needed <to CPC> with <productId>
When  Update quantity to <up qty> on 1 position with <prefix>
Then  Check on <productId> with <prefix> error message should be '<error after update qty>'
When  Go to checkout page if needed <comeBack>
Then  Check on <productId> with <prefix> error message should be '<error after update qty>'
Then  Remove item from trolley on 1 position

Examples:
| productId | qty | prefix |  delivery message                           | collection message                 |  to CPC |comeBack| up qty | error after update qty                                                                                   |
|    10687  | 10  |        |Not currently available for Next Day Delivery|Collection Unavailable Out of Stock | false   | false  |  11    |This product is out of stock. Please remove item from basket and look for an alternative                  |
|    22070  | 10  |  CPC_  |Not currently available for Next Day Delivery|10 in stock to Collect Today        | true    | false  |  12    |Yeovil doesn't have enough stock to fulfil your order. Please select the option below or try another store|
|    18187  | 10  |  CPC_  |Available for Next Day Delivery              |100 in stock to Collect Today       | true    | true   |  20    |empty                                                                                                       |