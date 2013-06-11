import os.path
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
  
dir="."  
print "Test 14 - good mutual X509"  
x509Cert = X509().parse(open(os.path.join(dir, "clientX509Cert.pem")).read())  
x509Chain = X509CertChain([x509Cert])  
s = open(os.path.join(dir, "clientX509Key.pem")).read()  
x509Key = parsePEMKey(s, private=True)  
  
#  
settings = HandshakeSettings()  
settings.minVersion = (3,0)  
settings.maxVersion = (3,0)  
connection.handshakeClientCert(x509Chain, x509Key, settings=settings)  
#  
  
#connection.handshakeClientCert(x509Chain, x509Key)  
  
assert(isinstance(connection.session.serverCertChain, X509CertChain))  
connection.close()  
connection.sock.close()