<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.tools.*"%>

<%
  CCI_TransAd ad = null;
  try {
    ad = (CCI_TransAd)session.getAttribute("ad");
  }
  catch (ClassCastException e) {
    ad = new CCI_TransAd();
  }

%>


    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Details and Price</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Make:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "make" maxlength = "50" value = "<%= ad.getMake() %>"/></span>
      <br><span class="edit-helptext">(Usedboats.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Length:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "10" name = "length" maxlength = "10" value = "<%= ad.getLength() %>"/></span>
      <br><span class="edit-helptext">(Usedboats.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Model:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "model" maxlength = "50" value = "<%= ad.getModel() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Type:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "type" maxlength = "50" value = "<%= ad.getType() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Year:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "year" maxlength = "10" value = "<%= ad.getYear() %>"/></span>
      <br><span class="edit-helptext">(Usedboats.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Engine:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "engine" maxlength = "50" value = "<%= ad.getEngine() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Price:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "20" name = "price" maxlength = "20" value = "<%= ad.getPrice() %>"/></span>
      <br><span class="edit-helptext">(Usedboats.com/Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Currency:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "10" name = "currency" maxlength = "10" value = "<%= ad.getCurrency() %>"/></span></td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Location</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Item Zip:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "itemZip" maxlength = "10" value = "<%= ad.getItemZip() %>"/></span>
         <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Contact Email for Enhanced Listing</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Enhancement Email:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "enhanceEmail" maxlength = "100" value = "<%= ad.getEnhanceEmail() %>"/></span>
      <br><span class="edit-helptext">(Usedboats.com - Optional)</span>
      </td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Contact Information</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Contact Phone:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "16" name = "contactPhone" maxlength = "16" value = "<%= ad.getContactPhone() %>"/></span> <span class="edit-helptext">(xxx-xxx-xxxx)</span>
        <br><span class="edit-helptext">(Compressor - Optional / <span class="edit-reqhelptext">Usedboats.com - Required to set up account</span>)</span>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Contact Email:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "60" name = "contactEmail" maxlength = "100" value = "<%= ad.getContactEmail() %>"/></span>
        <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
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

