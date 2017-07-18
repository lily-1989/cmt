<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.tools.*"%>

<%
  CCI_REAd ad = null;
  try {
    ad = (CCI_REAd)session.getAttribute("ad");
  }
  catch (ClassCastException e) {
    ad = new CCI_REAd();
  }
%>

    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Comm. Property Details</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Property Type:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "propertyType" maxlength = "50" value = "<%= ad.getPropertyType() %>"/></span>
      <br><span class="edit-reqhelptext">(Cityfeet.com - Required)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Sale Lease:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "2" name = "saleLease" maxlength = "2" value = "<%= ad.getSaleLease() %>"/></span>
      <br><span class="edit-reqhelptext">(Cityfeet.com - Required)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Square Footage:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "8" name = "squareFeet" maxlength = "8" value = "<%= ad.getSquareFeet() %>"/></span>
      <br><span class="edit-helptext">(Cityfeet.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Price per Sq Foot:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "20" name = "pricePerSqFt" maxlength = "20" value = "<%= ad.getPricePerSqFt() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Price:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "20" name = "price" maxlength = "20" value = "<%= ad.getPrice() %>"/></span>
      <br><span class="edit-helptext">(Cityfeet.com/Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Comm. Property Location</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Property City:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "propertyCity" maxlength = "50" value = "<%= ad.getPropertyCity() %>"/></span>
      <br><span class="edit-helptext">(Cityfeet.com/Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Property State:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "3" name = "propertyState" maxlength = "3" value = "<%= ad.getPropertyState() %>"/></span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Property Zip:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "propertyZip" maxlength = "10" value = "<%= ad.getPropertyZip() %>"/></span>
      <br><span class="edit-helptext">(<span class="edit-reqhelptext">Cityfeet.com - Required</span>, Compressor - Optional)</span>
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
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Property Contact Information</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Contact Phone:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "16" name = "contactPhone" maxlength = "16" value = "<%= ad.getContactPhone() %>"/></span> <span class="edit-helptext">(xxx-xxx-xxxx)</span>
      <br><span class="edit-helptext">(Cityfeet.com/Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Contact Email:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "60" name = "contactEmail" maxlength = "100" value = "<%= ad.getContactEmail() %>"/></span>
      <br><span class="edit-helptext">(Cityfeet.com/Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Agent URL:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "agentUrl" maxlength = "100" value = "<%= ad.getAgentUrl() %>"/></span></td>
    </tr>