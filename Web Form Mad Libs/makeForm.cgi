#!/usr/bin/env python
import cgitb
cgitb.enable()

import random

num = int(5 * random.random())
blanks = [('verb','noun','adjective'),('verb','adverb','noun'),('adjective','number','verb'),('noun','animal','verb'),('adjective','verb','color')]
choice = blanks[num]


print 'Content-Type: text/html'
print
print '<head>'
print '<link href="bootstrap.css" rel="stylesheet">'
print "<title>Zack's Mad Libs</title>"
print '</head>'
print '<div style="text-align:center">'
print "<h1 style='line-height:45px'>Welcome to Zack's Mad Libs Game!</h1>"
print '<div class="well">'
print '<form action="makeStory.cgi">'
print 'Please enter a %s:' %(choice[0])
print '<input type=text name=blank0 placeholder="some random word">'
print '<br></br>'
print 'Please enter a %s:' %(choice[1])
print '<input type=text name=blank1 placeholder="another random word">'
print '<br></br>'
print 'Please enter a %s:' %(choice[2])
print '<input type=text name=blank2 placeholder="the most random word">'
print '<br></br>'
print '<input type=hidden name=madlibnumber value=%d>' %(num)
print '<input type=submit class="btn btn-primary" value="Submit">'
print '</form>'
print '</div>'
print '</div>'