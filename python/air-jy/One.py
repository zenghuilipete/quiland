# -*- coding: utf-8 -*-
import sys
from optparse import OptionParser

greetings = dict(en=u'Hello %s!',
                 es=u'Hola %s!',
                 fr=u'Bonjour %s!',
                 pt=u'Alò %s!')

uis = {}
def register_ui(ui_name):
    def decorator(f):
        uis[ui_name] = f
        return f
    return decorator

def message(ui, msg):
    if ui in uis:
        uis[ui](msg)
    else:
        raise ValueError("No greeter named %s" % ui)

def list_uis():
    return uis.keys()

@register_ui('console')
def print_message(msg):
    print msg

@register_ui('window')
def show_message_as_window(msg):
    from javax.swing import JFrame, JLabel
    frame = JFrame(msg,
                   defaultCloseOperation=JFrame.EXIT_ON_CLOSE,
                   size=(100, 100),
                   visible=True)
    frame.contentPane.add(JLabel(msg))

if __name__ == "__main__":
    parser = OptionParser()
    parser.add_option('--ui', dest='ui', default='console',
                      help="Sets the UI to use to greet the user. One of: %s" % 
                      ", ".join("'%s'" % ui for ui in list_uis()))
    parser.add_option('--lang', dest='lang', default='en',
                      help="Sets the language to use")
    options, args = parser.parse_args(sys.argv)
    if len(args) < 2:
        print "Sorry, I can't greet you if you don't say your name"
        sys.exit(1)

    if options.lang not in greetings:
        print "Sorry, I don't speak '%s'" % options.lang
        sys.exit(1)

    msg = greetings[options.lang] % args[1]

    try:
        message(options.ui, msg)
    except ValueError, e:
        print "Invalid UI name\n"
        print "Valid UIs:\n\n" + "\n".join(' * ' + ui for ui in list_uis())
        sys.exit(1)
