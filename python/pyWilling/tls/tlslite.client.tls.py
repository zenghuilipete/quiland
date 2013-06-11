import socket
from tlslite.api import *
address = ("localhost", 4443)

def connect():
	sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	if hasattr(sock, 'settimeout'): #It's a python 2.3 feature
	    sock.settimeout(5)
	sock.connect(address)
	c = TLSConnection(sock)
	return c

connection = connect()

#
settings = HandshakeSettings()
settings.minVersion = (3,0)
settings.maxVersion = (3,0)
connection.handshakeClientCert(settings=settings)
#

#connection.handshakeClientCert()

assert(isinstance(connection.session.serverCertChain, X509CertChain))
connection.close()
connection.sock.close()