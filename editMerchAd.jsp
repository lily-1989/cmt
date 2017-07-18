<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.tools.*"%>

<%
  CCI_GenAd ad = null;
  try {
    ad = (CCI_GenAd)session.getAttribute("ad");
  }
  catch (ClassCastException e) {
    ad = new CCI_GenAd();
  }

%>

    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Item Description</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Type:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "type" maxlength = "50" value = "<%= ad.getType() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Price:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "20" name = "price" maxlength = "20" value = "<%= ad.getPrice()%>"/></span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">OBO:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="oBO" value="1" <% if (new Integer(1).equals(ad.getoBO())) { %>checked<% } %> />
      No <input type="radio" name="oBO" value="0" <% if (new Integer(0).equals(ad.getoBO())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Item Location</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Zip:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "zip" maxlength = "10" value = "<%= ad.getZip() %>"/></span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
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
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Contact Info</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Contact Phone:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "16" name = "contactPhone" maxlength = "16" value = "<%= ad.getContactPhone() %>"/></span> <span class="edit-helptext">(xxx-xxx-xxxx)</span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Contact Email:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "60" name = "contactEmail" maxlength = "100" value = "<%= ad.getContactEmail() %>"/></span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>