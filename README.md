# Service-Centric and Cloud Computing

This repository contains the code from my SCC coursework, but it has been modified since: the Scala and Golang elements have been modified so that gRPC is used by 
the Scala Web Service to call the methods on the Golang one. This has been done purely because I wanted a test bed on which to try out gRPC,
and this existing codebase seemed like not too bad a choice.

The following is an extract from the report I wrote for this project. The system's purpose is to simulate the trading of shares,
and to log the shares owned by different users. The software was written to a specification which detailed the purpose of the system,
but not specifics of implementation, which was left relatively open-ended. As such, I chose to use Scala/Scalatra/Slick,
Go, and Angular - largely because I wanted to learn more about each of these.

![](https://www.danrh.co.uk/images/uni/scc_uml.png)

The system is built around a web server which provides a REST API for consumption by the
client. This web server itself makes use of two other REST services, one of which is external
(WorldTradingData.com), the other of which has been developed alongside the main web
service. This other web service is written in Go to cache the daily exchange rates from the
European Central Bank (via exchangeratesapi.io), and to provide these to the main server via
a REST API.

The REST API on the main web service provides functions to create a user account, to login
and generate an API key, to get the shares and quantities for the user (and apply search and
order-by criteria to this), to make share transactions, to list all shares (and to also apply
search and order-by criteria to this), and to remove shares from a user’s account.
The Currency Converter Service provides methods to convert currencies, to list all currency
codes, and to get exchange rates.

![](https://www.danrh.co.uk/images/uni/scc_scr1.png)

On opening the web-based client, the user is required to log-in or register, then presented with
a list of the shares that they are tracking, and in what quantities. They may search this list of
shares, change the display currency, sort them by any of the columns on the table. The user
may buy and sell shares which they are tracking. The User can open a list of all shares to view
and search all shares. From this they may select additional shares to track. 





