import csv
def makeData(symbols,final):
    reader = csv.reader(final, delimiter=',')
    dic = {}
    dic_price = {}
    for row in reader:
        symbol = row[0].split(' ')[0]
        name = row[1]
        if(symbol in symbols):
           dic[symbol] = name
    for symbol in symbols:
        filename = "./histData/"+symbol+".csv"
        with open(filename) as f_obj:
            rdr = csv.reader(f_obj,delimiter=',')
            tp = [0,0,0]
            for row_ in rdr:
                if(row_[0]=="2017-03-08"):
                    tp[0] = float(row_[6])
                if(row_[0]=="2017-03-09"):
                    tp[1] = float(row_[6])
            tp[2] = (tp[1]-tp[0])*100/tp[0]
            tp[0] = "{0:.2f}".format(tp[0])
            tp[1] = "{0:.2f}".format(tp[1])
            tp[2] = "{0:.2f}%".format(tp[2])
        dic_price[symbol] = tp
    #print(dic_price)
    with open("info.txt",'w') as w_obj:
        for symbol in symbols:
            w_obj.write("dic."+symbol+"={symbol:\""+symbol+"\",name:\""+dic[symbol]+"\",price:\""+dic_price[symbol][1]+"\",percentage:\""+dic_price[symbol][2]+"\"};\n")
		


if __name__ == "__main__":
    symbols = ["STO","XOM","CVX","BP","DHR","BUD","ABT","HAL","INTC","AMZN","DD","KHC","UL","INFY","VOD","MMM","FOX","HDB","WBA","LLY","RELX","EOG","GSK","WBK","PUK","LMT","CMCSA","EPD","SLB","KMI","TOT","MDLZ","CELG","ASML","SNP","COP","MCK","ORAN","GOOGL","NVS","MTU","OXY","UTX","NGG","GIS","SAP","AVGO","IBM","SO","TJX","UNP","GE","SNP","NEE","MFG","GILD","CHA","PTR","TEVA","OXY","FOX","ORAN"]

    csv_path = "final.csv"
    with open(csv_path) as f_obj:
        makeData(symbols,f_obj)
	
