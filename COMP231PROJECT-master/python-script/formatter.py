def getSymbols():
    with open("../data/symbols.csv","r") as f:
        symbols = f.readlines()
    f.close()
    return symbols


def correct(symbol):
	html = "./htmls/"+symbol+"_candlestick.html"
	try:
		with open(html,"r") as f:
        		contents = f.readlines()
		f.close()
		html = "./htmls_re/"+symbol+".html"
		thefile = open(html, 'w')
		for i in range(0,len(contents)):
			if(i==4):
				contents[i] = contents[i]+"</meta>\n"
			if(i==25):
				contents[i] = contents[i]+"<a th:href=\"${\'/result\'}\" th:utext=\"back\"></a>\n"
			#print(contents[i])
			thefile.write("%s" % contents[i])
	#cleans = []
	#for line in contents:
	#	cleans.append(line.rstrip('\n'))
	#print(line[4])
	except:
		return
	
		


symbols_ = getSymbols()
symbols = []
for symbol in symbols_:
	symbols.append(symbol.rstrip('\n'))
#symbols = ["GOOG"]
for symbol in symbols:
	correct(symbol)
