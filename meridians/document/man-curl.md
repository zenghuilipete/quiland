curl -H "Accept:application/json" http://localhost:8080/simple-service-webapp-spring-jpa-jquery/webapi/books/book?id=1
{"bookId":1,"bookName":"Java Restful Web Service使用指南","publisher":"cmpbook"}

curl http://localhost:8080/simple-service-webapp-spring-jpa-jquery/webapi/books/{1,2}
C:\Users\Administrator>curl http://localhost:8080/simple-service-webapp-spring-jpa-jquery/webapi/books/{1,2}

[1/2]: http://localhost:8080/simple-service-webapp-spring-jpa-jquery/webapi/books/1 --> <stdout>
--_curl_--http://localhost:8080/simple-service-webapp-spring-jpa-jquery/webapi/books/1
<?xml version="1.0" encoding="UTF-8" standalone="yes"?><book bookId="1" bookName="Java Restful Web Service使用指南" publisher="cmpbook"/>

[2/2]: http://localhost:8080/simple-service-webapp-spring-jpa-jquery/webapi/books/2 --> <stdout>
--_curl_--http://localhost:8080/simple-service-webapp-spring-jpa-jquery/webapi/books/2
<?xml version="1.0" encoding="UTF-8" standalone="yes"?><book bookId="2" bookName="JSF2和RichFaces4使用指南" publisher="phei"/>

curl -v -X POST -H "Content-Type:application/xml" -H "Accept: application/xml" http://localhost:8080/simple-service-webapp-spring-jpa-jquery/webapi/books --data-binary "<book bookName='JAX-RS2'/>"

       > POST /simple-service-webapp-spring-jpa-jquery/webapi/books HTTP/1.1
       > User-Agent: curl/7.26.0
       > Host: localhost:8080
       > Content-Type:application/xml
       > Accept: application/xml
       > Content-Length: 26
       >
       * upload completely sent off: 26 out of 26 bytes
       < HTTP/1.1 200 OK
       < Server: Apache-Coyote/1.1
       < Content-Type: application/xml
       < Content-Length: 93
       < Date: Sun, 01 Sep 2013 05:30:59 GMT
       <
       <?xml version="1.0" encoding="UTF-8" standalone="yes"?><book bookId="14" bookName="JAX-RS2"/>

curl -v -X PUT -H "Content-Type:application/xml" -H "Accept: application/xml" http://localhost:8080/simple-service-webapp-spring-jpa-jquery/webapi/books/2 --data-binary "<book bookName='JAX-RS2' publisher='CMP'/>"

       > PUT /simple-service-webapp-spring-jpa-jquery/webapi/books/2 HTTP/1.1
       > User-Agent: curl/7.26.0
       > Host: localhost:8080
       > Content-Type:application/xml
       > Accept: application/xml
       > Content-Length: 42
       >
       * upload completely sent off: 42 out of 42 bytes
       < HTTP/1.1 200 OK
       < Server: Apache-Coyote/1.1
       < Content-Type: application/xml
       < Content-Length: 108
       < Date: Sun, 01 Sep 2013 05:36:13 GMT
       <
       <?xml version="1.0" encoding="UTF-8" standalone="yes"?><book bookId="2" bookName="JAX-RS2" publisher="CMP"/>


curl -v -X PUT -H "Content-Type:application/json" http://localhost:8080/simple-service-webapp-spring-jpa-jquery/webapi/books/3 -d "{\"bookName\":\"JAX-RS2\",\"publisher\":\"CMP\"}"

       > PUT /simple-service-webapp-spring-jpa-jquery/webapi/books/3 HTTP/1.1
       > User-Agent: curl/7.26.0
       > Host: localhost:8080
       > Accept: */*
       > Content-Type:application/json
       > Content-Length: 40
       >
       * upload completely sent off: 40 out of 40 bytes
       < HTTP/1.1 200 OK
       < Server: Apache-Coyote/1.1
       < Content-Type: application/xml
       < Content-Length: 108
       < Date: Mon, 16 Sep 2013 13:46:27 GMT
       <
       <?xml version="1.0" encoding="UTF-8" standalone="yes"?><book bookId="3" bookName="JAX-RS2" publisher="CMP"/>

curl -H "Accept: application/json" http://localhost:8080/simple-service-webapp-spring-jpa-jquery/webapi/books

       {"bookList":{"book":[{"bookId":1,"bookName":"Java Restful Web Service使用指南","publisher":"cmpbook"},{"bookId":2,"bookName":"JSF2和RichFaces4使用指
       南","publisher":"phei"}]}}

curl支持的协议
DICT, FILE, FTP, FTPS, GOPHER, HTTP, HTTPS,  IMAP, IMAPS,  LDAP,  LDAPS,  POP3, POP3S, RTMP, RTSP, SCP, SFTP, SMTP, SMTPS,
TELNET and TFTP

URL/RFC 3986
       使用一组URL
       http://site.{one,two,three}.com
       ftp://ftp.numericals.com/file[1-100].txt
       ftp://ftp.numericals.com/file[001-100].txt    (with leading zeros)
       ftp://ftp.letters.com/file[a-z].txt
       http://www.numericals.com/file[1-100:10].txt
       http://www.letters.com/file[a-z:2].txt
       http://any.org/archive[1996-1999]/vol[1-4]/part{a,b,c}.html

-#, --progress-bar 进度条
-0, --http1.0
-1, --tlsv1
-2, --sslv2
-3, --sslv3
-4, --ipv4
-6, --ipv6

--anyauth  --basic, --digest, --ntlm, and --negotiate.
-b, --cookie <name=data>
              (HTTP)  Pass the data to the HTTP server as a cookie. It is sup‐
              posedly the data previously received from the server in a  "Set-
              Cookie:"  line.  The data should be in the format "NAME1=VALUE1;
              NAME2=VALUE2".
--connect-timeout <seconds>
-d, --data <data>
-F, --form <name=content>
              curl -F password=@/etc/passwd www.mypasswords.com
              curl -F "web=@index.html;type=text/html" url.com
              curl -F "name=daniel;type=text/foo" url.com
              curl -F "file=@localfile;filename=nameinpost" url.com
-H, --header <header>
-m, --max-time <seconds>
-N, --no-buffer
-o, --output <file>
-u, --user <user:password>
-U, --proxy-user <user:password>
--url <URL>
-v, --verbose
-w, --write-out <format>
-x, --proxy <[protocol://][user@password]proxyhost[:port]>
-X, --request <command>
              (HTTP) Specifies a custom request method to use when communicat‐
              ing  with  the  HTTP server.  The specified request will be used
              instead of the method otherwise used (which  defaults  to  GET).
              Read  the  HTTP  1.1 specification for details and explanations.
              Common additional HTTP requests  include  PUT  and  DELETE,  but
              related technologies like WebDAV offers PROPFIND, COPY, MOVE and
              more.