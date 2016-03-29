mvn clean tomcat7:run

users are in db:
Email:admin@mail.com, Pass:123456
Email:user@mail.com, Pass:1111
http://localhost:8080/TicketsBooking/bookedTickets?pageSize=5&pageNum=1&title=eve&action=Submit <- only for admin
http://localhost:8080/TicketsBooking/UploadEntity
http://localhost:8080/TicketsBooking/bookedTickets.pdf?pageSize=5&pageNum=1&userId=1
http://localhost:8080/TicketsBooking/event
http://localhost:8080/TicketsBooking/events