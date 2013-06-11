import socket, ssl, pprint

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# require a certificate from the server
ssl_sock = ssl.wrap_socket(s,
                           ca_certs="/etc/ca_certs_file",
                           cert_reqs=ssl.CERT_REQUIRED)

ssl_sock.connect(('localhost', 9527))

print repr(ssl_sock.getpeername())
print ssl_sock.cipher()
print pprint.pformat(ssl_sock.getpeercert())

ssl_sock.write("client say hi.")

# read all the data returned by the server.
data = ssl_sock.read()
print data
# note that closing the SSLSocket will also close the underlying socket
ssl_sock.close()
