import os.path
import socket

from tlslite.api import *

address = ("localhost", 4443)
lsock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
lsock.bind(address)
lsock.listen(5)

def connect():
	return TLSConnection(lsock.accept()[0])

connection = connect()

dir="."

x509Cert = X509().parse(open(os.path.join(dir, "serverX509Cert.pem")).read())
x509Chain = X509CertChain([x509Cert])

s = open(os.path.join(dir, "serverX509Key.pem")).read()
x509Key = parsePEMKey(s, private=True)

#
settings = HandshakeSettings()
settings.minVersion = (3,0)
settings.maxVersion = (3,0)
connection.handshakeServer(certChain=x509Chain, privateKey=x509Key, settings=settings)
#

#connection.handshakeServer(certChain=x509Chain, privateKey=x509Key)

connection.close()
connection.sock.close()
