"""
Project 3 - Basic Web Programming
CS9H
Zack Mayeda
cs9h-co

My program asks the user for a movie or tv show, and returns a list of the actors and actresses
in the specified movie or show. Information is gathered from IMDB. 
"""
import urllib
import urllib2
import re
import htmlentitydefs
import doctest

def main():
	"""
	Main function that greets user, ask for a movie or tv show,
	sends the title to the getList function,
	and loops until the user is done using the program.
	There are no inputs or outputs of this function.
	"""
	print "Welcome to Zack's Cast List Mini App!"
	while True:
		search = raw_input("What movie or tv show would you like the cast list for? ")
		getList(search)
		print 'Would you like to search again? [yes / no] ',
		answer = raw_input()
		answer = answer.lower()
		if answer == "no" or answer == "n":
			break

def getList(search):
	"""
	This function does the bulk of the work in this program.
	It searches IMDB for the given title, retrieves the top result,
	pulls out all the cast and characters, and prints them out.
	"""
	# Loop until valid URL is found, and the movie found is what the user wants
	while True:
		search = urllib2.quote(search)
		searchURL = ''
		# search for the given title, return error if it can't find a result
		try:
			searchURL = urllib2.urlopen("http://www.imdb.com/find?q="+search+"&s=all")
		except urllib2.HTTPError:
			print "I can't seem to find that movie. Please try again."
			search = raw_input("What movie or tv show would you like the cast list for? ")
			continue
		searchHTML = searchURL.read()
		# find first <td valign="top">
		num = searchHTML.find('<td valign="top">')
		# adjust html to cut out top
		searchHTML = searchHTML[num+17:]

		# find next<td valign="top">
		num = searchHTML.find('<td valign="top">')
		# adjust html to cut out more
		searchHTML = searchHTML[num+17:]

		# find the next <a href=
		num = searchHTML.find('<a href="')
		searchHTML = searchHTML[num+9:]

		# find next "
		num = searchHTML.find('"')
		movieURL = 'http://www.imdb.com/' + searchHTML[1:num]
		searchHTML = searchHTML[num+1:]

		# find next > and then <
		num = searchHTML.find('>')
		endNum = searchHTML.find('<')
		movieName = searchHTML[num+1:endNum]
		movieName = re.sub(r'&#x22;','',movieName)
		movieName = convert_html_entities(movieName)

		# open the movie page, make sure it exists
		try:
			searchURL = urllib2.urlopen(movieURL)
		except urllib2.HTTPError:
			print "Sorry, an error occurred. Please try again."
			search = raw_input("What movie or tv show would you like the cast list for? ")
			continue
		searchHTML = searchURL.read()

		# ask user if the found movie is what they were looking for
		print 'Found a listing for %s. Is that correct? [yes / no] ' %(movieName),
		answer = raw_input()
		answer = answer.lower()
		# either continue on and find cast list, or get new input from user
		if answer == 'yes' or answer == 'y':
			break
		else:
			search = raw_input("Ok, please try again. What movie or tv show would you like the cast list for? ")

	searchURL.close()

	print 'Found IMDB page for: %s. Looking up cast list now.\n' %(movieName)

	# search for <table class="cast_list">
	num = searchHTML.find('<table class="cast_list">')
	searchHTML = searchHTML[num+25:]
	
	# remove top of page, up to </tr> and before </table>
	num = searchHTML.find('</tr>')
	searchHTML = searchHTML[num+5:]
	num = searchHTML.find('</table>')
	searchHTML = searchHTML[:num+8]

	actorList = []
	# pull out cast list and put into list by actor/actress
	while searchHTML.find('</table>') > 0:
		start = searchHTML.find('<tr class=')
		end = searchHTML.find('</tr>')
		actorBlock = searchHTML[start+10:end]
		actorList.append(actorBlock)
		searchHTML = searchHTML[end+5:]

	# get rid of extra empty space in list
	actorList = actorList[:len(actorList)-1]
	# go through list of actors/actresses and pull out just name and role, put back in list as tuple
	actorList = getActorList(actorList)

	print '~'*(13+len(movieName))
	print 'The cast of %s:' % (movieName)
	print 'Actor/Actress (character)'
	print '-'*(13+len(movieName))+'\n'
	# print out the cast
	for namePair in actorList:
		actor,role = namePair
		print "%s (%s)" % (actor,role)
	searchURL.close()

def getActorList(actorList):
	"""
	Given a list with a chunk of html for each actor, this
	function parses through the html and pulls out the actor/
	actress name. It returns a list with tuples of the name
	and role in the list instead of the html.
	"""
	for i in range(len(actorList)):
		block = actorList[i]
		num = block.find('<td class="name">')
		block = block[num+17:]
		num = block.find('>')
		block = block[num+1:]
		num = block.find('<')
		# pull out the actor name and turn ascii and unicode into characters
		name = convert_html_entities(block[:num])

		num = block.find('<td class="character">')
		block = block[num+22:]
		num = block.find('>')
		block = block[num+1:]

		if block.find('<a') > 0:
			num = block.find('>')
			block = block[num+1:]
		else:
			num = block.find('<')
			block = block[:num]
			block = block.strip() + '<'

		num = block.find('<')
		role = block[:num]
		# pull out their role in the movie and convert ascii and unicode
		role = convert_html_entities(role)
		# get rid of tabs, newlines
		role = role.replace('\t','')
		role = role.replace('\n','')

		actorList[i] = (name,role)
	return actorList

def convert_html_entities(s):
	"""
	Function that I found online that converts ASCII and unicode to characters.
	Uses regular expressions to find sections to convert and the unichr function
	to replace the sections. Also uses the htmlentitydefs library.
	"""
	matches = re.findall("&#\d+;", s)
	if len(matches) > 0:
		hits = set(matches)
		for hit in hits:
			name = hit[2:-1]
			try:
				entnum = int(name)
				s = s.replace(hit, unichr(entnum))
			except ValueError:
				pass

	matches = re.findall("&#[xX][0-9a-fA-F]+;", s)
	if len(matches) > 0:
		hits = set(matches)
		for hit in hits:
			hex = hit[3:-1]
			try:
				entnum = int(hex, 16)
				s = s.replace(hit, unichr(entnum))
			except ValueError:
				pass

	matches = re.findall("&\w+;", s)
	hits = set(matches)
	amp = "&amp;"
	if amp in hits:
		hits.remove(amp)
	for hit in hits:
		name = hit[1:-1]
		if htmlentitydefs.name2codepoint.has_key(name):
			s = s.replace(hit, unichr(htmlentitydefs.name2codepoint[name]))
	s = s.replace(amp, "&")
	return s 

# Calls the doctest test functions and then calls the main function.
if __name__ == "__main__":
	doctest.testmod()
	main()