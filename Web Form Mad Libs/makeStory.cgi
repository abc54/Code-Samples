#!/usr/bin/env python
import cgitb
cgitb.enable()

stories = [0,1,2,3,4]
stories[0] = 'Alice was going to','a message to Bob, but Eve used a','to steal the','message.'
stories[1] = 'Bob wanted to','the huge sandwich for breakfast',', unfortunately he found a','in it and lost his appetite.'
stories[2] = 'Ryan was feeling particularly',', so he picked up','books from the library and proceeded to','them all.'
stories[3] = 'One day, there was a sad','that just wanted to become a','but as much as it tried it could only manage to','.'
stories[4] = 'One day in Berkeley, the most', 'student in the city decided to','near the campanile and then painted it','.'

import cgi
form = cgi.FieldStorage()

word0 = form.getvalue('blank0','word')
word1 = form.getvalue('blank1','word')
word2 = form.getvalue('blank2','word')
num = int(form.getvalue('madlibnumber','0'))

print 'Content-Type: text/html'
print
print '<div style="text-align:center">'
print '<head>'
print '<link href="bootstrap.css" rel="stylesheet">'
print "<title>Zack's Mad Libs | Your Story</title>"
print '</head>'
print '<h1 style="line-height:50px">Here is your story!</h1>'
print '<div class="well">'
print '<p class="lead">' + stories[num][0], word0, stories[num][1], word1, stories[num][2], word2, stories[num][3], '</p>'
print '<a href=makeForm.cgi>play again</a>'
print '</div>'
print '</div'