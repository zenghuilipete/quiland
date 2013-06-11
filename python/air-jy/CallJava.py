from creative.air.jy.java import Mantra 

try:
    s = "let's complete the ci and architect in 2013.";
    mantra = Mantra();
    s1 = mantra.fog(s);
    print s1;
    print mantra.unfog(s1);
except Exception, e:
    print e
