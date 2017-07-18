<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.tools.*"%>

<%
  CCI_AptAd ad = null;
  try {
    ad = (CCI_AptAd)session.getAttribute("ad");
  }
  catch (ClassCastException e) {
    ad = new CCI_AptAd();
  }

%>

    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Property Details</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Property Type:</span><span class="edit-reqlabel">*</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "propertyType" maxlength = "50" value = "<%= ad.getPropertyType() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Bedrooms:</span><span class="edit-reqlabel">*</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "3" name = "bedrooms" maxlength = "3" value = "<%= ad.getBedrooms() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Baths:</span><span class="edit-reqlabel">*</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "4" name = "baths" maxlength = "4" value = "<%= ad.getBaths() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Price:</span><span class="edit-reqlabel">*</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "20" name = "price" maxlength = "20" value = "<%= ad.getPrice() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">MLS Number:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "20" name = "mlsNumber" maxlength = "20" value = "<%= ad.getMlsNumber() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Square Feet:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "8" name = "squareFeet" maxlength = "8" value = "<%= ad.getSquareFeet() %>"/></span></td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Property Location</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Neighborhood:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "neighborhood" maxlength = "100" value = "<%= ad.getNeighborhood() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Property Address:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "60" name = "propertyAddress" maxlength = "200" value = "<%= ad.getPropertyAddress() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Property City:</span><span class="edit-reqlabel">*</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "propertyCity" maxlength = "50" value = "<%= ad.getPropertyCity() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Property State:</span><span class="edit-reqlabel">*</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "3" name = "propertyState" maxlength = "3" value = "<%= ad.getPropertyState() %>"/></span></td>
    </tr>
        <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Property Zip:</span><span class="edit-reqlabel">*</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "propertyZip" maxlength = "10" value = "<%= ad.getPropertyZip() %>"/></span>
      <br><span class="edit-helptext">(Apartments.com - Optional, <span class="edit-reqhelptext">Required for Combo Ads</span>)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Country:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "country" maxlength = "50" value = "<%= ad.getCountry() %>"/></span></td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Open House</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Open House Date 1:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "openHouseDate1" maxlength = "10" value = "<%= ad.getOpenHouseDate1() == null ? "" : ad.getDateAsShortString(ad.getOpenHouseDate1()) %>"/></span>
          <A HREF="#" onClick="cal.select(document.forms['adSubmit'].openHouseDate1,'anchor.openHouseDate1','yyyy/MM/dd'); return false;" NAME="anchor.openHouseDate1" ID="anchor.openHouseDate1"><img src = "images/cal.gif" border="0" alt="calendar"></A>
          &nbsp<span class="edit-helptext">(yyyy/mm/dd)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Open House Date 2:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "10" name = "openHouseDate2" maxlength = "10" value = "<%= ad.getOpenHouseDate2() == null ? "" : ad.getDateAsShortString(ad.getOpenHouseDate2()) %>"/></span>
          <A HREF="#" onClick="cal.select(document.forms['adSubmit'].openHouseDate2,'anchor.openHouseDate2','yyyy/MM/dd'); return false;" NAME="anchor.openHouseDate2" ID="anchor.openHouseDate2"><img src = "images/cal.gif" border="0" alt="calendar"></A>
          &nbsp<span class="edit-helptext">(yyyy/mm/dd)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Open House Start Time 1:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "openHouseStartTime1" maxlength = "10" value = "<%= ad.getOpenHouseStartTime1() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Open House Start Time 2:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "10" name = "openHouseStartTime2" maxlength = "10" value = "<%= ad.getOpenHouseStartTime2() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Open House End Time 1:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "openHouseEndTime1" maxlength = "10" value = "<%= ad.getOpenHouseEndTime1() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Open House End Time 2:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "10" name = "openHouseEndTime2" maxlength = "10" value = "<%= ad.getOpenHouseEndTime2() %>"/></span></td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Property Amenities</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Air Conditioning:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "2" name = "airCondition" maxlength = "2" value = "<%= ad.getAirCondition() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Basement:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "2" name = "basement" maxlength = "2" value = "<%= ad.getBasement() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Dishwasher:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "2" name = "dishwasher" maxlength = "2" value = "<%= ad.getDishwasher() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Elevator:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "2" name = "elevator" maxlength = "2" value = "<%= ad.getElevator() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Fireplace:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "2" name = "fireplace" maxlength = "2" value = "<%= ad.getFireplace() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Fitness Room:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "2" name = "fitnessRoom" maxlength = "2" value = "<%= ad.getFitnessRoom() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Dining Room:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "2" name = "diningRoom" maxlength = "2" value = "<%= ad.getDiningRoom() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Family Room:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "2" name = "familyRoom" maxlength = "2" value = "<%= ad.getFamilyRoom() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Laundry:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "2" name = "laundry" maxlength = "2" value = "<%= ad.getLaundry() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Other Parking:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "2" name = "otherParking" maxlength = "2" value = "<%= ad.getOtherParking() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Garage Parking:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "2" name = "garageParking" maxlength = "2" value = "<%= ad.getGarageParking() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Pets:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "2" name = "pets" maxlength = "2" value = "<%= ad.getPets() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Pool:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "2" name = "pool" maxlength = "2" value = "<%= ad.getPool() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Security:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "2" name = "security" maxlength = "2" value = "<%= ad.getSecurity() %>"/></span></td>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Contact Email for Enhanced Listing</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Enhancement Email:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "enhanceEmail" maxlength = "100" value = "<%= ad.getEnhanceEmail() %>"/></span>
      <br><span class="edit-helptext">(Apartments.com - Optional, <span class="edit-reqhelptext">Required for Combo Ads</span>)</span>
      </td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Property Contact Information</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Contact Phone:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "16" name = "contactPhone" maxlength = "16" value = "<%= ad.getContactPhone() %>"/></span> <span class="edit-helptext">(xxx-xxx-xxxx)</span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Contact Email:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "60" name = "contactEmail" maxlength = "100" value = "<%= ad.getContactEmail() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Agent URL:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "agentUrl" maxlength = "100" value = "<%= ad.getAgentUrl() %>"/></span></td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Web ID</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Web ID:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "20" name = "webID" maxlength = "20" value = "<%= ad.getWebID() %>"/></span></td>
    </tr>

