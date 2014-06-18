package de.haw.mps.offer.api;

import de.haw.mps.fabrication.entity.ElementEntity;
import de.haw.mps.offer.entity.CustomerEntity;
import de.haw.mps.offer.entity.OfferEntity;
import de.haw.mps.offer.entity.OrderEntity;

import java.text.SimpleDateFormat;
import java.util.Set;

public class OrderHelper {

    public static final int PARAM_COUNT = 9;

    public static final int POS_ID = 0;
    public static final int POS_OFFERLIST = 1;
    public static final int POS_CUSTOMERID = 2;
    public static final int POS_CUSTOMERNAME = 3;
    public static final int POS_ELEMENTID = 4;
    public static final int POS_ELEMENTNAME = 5;
    public static final int POS_ORDERDATE = 6;
    public static final int POS_SHIPPINGDATE = 7;
    public static final int POS_INVOICEDATE = 8;

    public static final String DATE_FORMAT = "DD-MMM-yyyy HH:mm";

    public static final String DEFAULT_CUSTOMERNAME = "&gt;No Customer &lt;";
    public static final String DEFAULT_ELEMENTNAME = "&gt;No Element &lt;";

    public static String[] getApiParameter(OrderEntity entity) {
        OfferEntity offerEntity = null;
        String[] params = new String[PARAM_COUNT];

        String offerIdList = "";
        Set<OfferEntity> offers = entity.getOffers();
        if(offers != null) {
            // concat to "|id1|id2|id3"
            for(OfferEntity cOfferEntity : offers) {
                offerIdList = offerIdList.concat(String.format("|%d", cOfferEntity.getId()));
                offerEntity = cOfferEntity;
            }
        }
        // strip first pipe
        if(offerIdList.length() > 0) {
            offerIdList = offerIdList.substring(1);
        }

        String customerName = DEFAULT_CUSTOMERNAME;
        long customerId = -1;
        if(offerEntity != null && offerEntity.getCustomer() != null) {
            CustomerEntity customerEntity = offerEntity.getCustomer();
            customerName = customerEntity.getName();
            customerId = customerEntity.getId();
        }

        String elementName = DEFAULT_ELEMENTNAME;
        long elementId = -1;
        if(offerEntity != null && offerEntity.getOrderObject() != null) {
            ElementEntity elementEntity = offerEntity.getOrderObject();
            elementName = elementEntity.getName();
            elementId = elementEntity.getId();
        }

        params[POS_ID] = String.valueOf(entity.getId());
        params[POS_OFFERLIST] = offerIdList;
        params[POS_CUSTOMERID] = String.valueOf(customerId);
        params[POS_CUSTOMERNAME] = customerName;
        params[POS_ELEMENTID] = String.valueOf(elementId);
        params[POS_ELEMENTNAME] = elementName;

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        params[POS_ORDERDATE] = formatter.format(entity.getOrderDate().getTime());
        params[POS_SHIPPINGDATE] = formatter.format(entity.getShippingDate().getTime());
        params[POS_INVOICEDATE] = formatter.format(entity.getInvoiceDate().getTime());

        return params;
    }

}
