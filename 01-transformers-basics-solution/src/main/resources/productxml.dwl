%dw 2.0
output application/xml
---
product @(pid:payload.productId):{
	
	productName:payload.name,
	offer:{
		offerPrice:payload.offer.offerPrice,
		offerValidUntil:payload.offer.offerValidUntil
	},	
	image1 :payload.images[0]
}
