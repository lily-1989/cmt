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
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Vehicle Basics</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Year:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "year" maxlength = "10" value = "<%= ad.getYear() %>"/></span>
      <br><span class="edit-helptext">(Cars.com - Optional)</span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Make:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "make" maxlength = "50" value = "<%= ad.getMake() %>"/></span>
      <br><span class="edit-helptext">(Cars.com - Optional)</span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Model:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "model" maxlength = "50" value = "<%= ad.getModel() %>"/></span>
      <br><span class="edit-helptext">(Cars.com - Optional)</span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">VIN:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "vin" maxlength = "50" value = "<%= ad.getVin() %>"/></span></td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Vehicle Details and Price</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Trim:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "trim" maxlength = "50" value = "<%= ad.getTrim() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Body Style:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "bodyStyle" maxlength = "50" value = "<%= ad.getBodyStyle() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Price:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "20" name = "price" maxlength = "20" value = "<%= ad.getPrice() %>"/></span>
      <br><span class="edit-helptext">(Cars.com/Compressor - Optional, <span class="edit-reqhelptext">Required for Combo Ads</span>)</span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Mileage:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "20" name = "mileage" maxlength = "20" value = "<%= ad.getMileage() %>"/></span>
      <br><span class="edit-helptext">(Cars.com - Optional)</span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">OBO:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="oBO" value="1" <% if (new Integer(1).equals(ad.getoBO())) { %>checked<% } %> />
      No <input type="radio" name="oBO" value="0" <% if (new Integer(0).equals(ad.getoBO())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Vehicle Zip:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "10" name = "vehicleZip" maxlength = "10" value = "<%= ad.getVehicleZip() %>"/></span>
      <br><span class="edit-helptext">(Cars.com/Compressor - Optional, <span class="edit-reqhelptext">Required for Combo Ads</span>)</span>
      </td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Additional Vehicle Info</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Exterior Color:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "exteriorColor" maxlength = "50" value = "<%= ad.getExteriorColor() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Condition:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "condition" maxlength = "50" value = "<%= ad.getCondition() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Interior Color:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "interiorColor" maxlength = "50" value = "<%= ad.getInteriorColor() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Doors:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "2" name = "doors" maxlength = "2" value = "<%= ad.getDoors() == null ? "" : ad.getDoors().toString() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Transmission:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "transmission" maxlength = "50" value = "<%= ad.getTransmission() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Engine:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "engine" maxlength = "50" value = "<%= ad.getEngine() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Drive Train:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "driveTrain" maxlength = "10" value = "<%= ad.getDriveTrain() %>"/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">License Plate Number:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "20" name = "licensePlateNumber" maxlength = "20" value = "<%= ad.getLicensePlateNumber() %>"/></span></td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Dealer Information</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Dealer ID:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "20" name = "dealerID" maxlength = "20" value = "<%= ad.getDealerID() == null ? "" : ad.getDealerID().toString() %>"/></span>
      <br><span class="edit-helptext">(Cars.com - Optional, <span class="edit-reqhelptext">Required for dealers</span>)</span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Stock Number:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "stockNumber" maxlength = "50" value = "<%= ad.getStockNumber() %>"/></span></td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Vehicle Features</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">AC Front:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="acFront" value="1" <% if (new Integer(1).equals(ad.getAcFront())) { %>checked<% } %> />
      No <input type="radio" name="acFront" value="0" <% if (new Integer(0).equals(ad.getAcFront())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">AC Rear:</span></th>
      <td class="edit-bg2"><span class="edit-text">
      Yes <input type = "radio" name="acRear" value="1" <% if (new Integer(1).equals(ad.getAcRear())) { %>checked<% } %> />
      No <input type="radio" name="acRear" value="0" <% if (new Integer(0).equals(ad.getAcRear())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Cruise Control:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="cruiseControl" value="1" <% if (new Integer(1).equals(ad.getCruiseControl())) { %>checked<% } %> />
      No <input type="radio" name="cruiseControl" value="0" <% if (new Integer(0).equals(ad.getCruiseControl())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Navigation:</span></th>
      <td class="edit-bg2"><span class="edit-text">
      Yes <input type = "radio" name="navigation" value="1" <% if (new Integer(1).equals(ad.getNavigation())) { %>checked<% } %> />
      No <input type="radio" name="navigation" value="0" <% if (new Integer(0).equals(ad.getNavigation())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Power Locks:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="powerLocks" value="1" <% if (new Integer(1).equals(ad.getPowerLocks())) { %>checked<% } %> />
      No <input type="radio" name="powerLocks" value="0" <% if (new Integer(0).equals(ad.getPowerLocks())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Power Steering:</span></th>
      <td class="edit-bg2"><span class="edit-text">
      Yes <input type = "radio" name="powerSteering" value="1" <% if (new Integer(1).equals(ad.getPowerSteering())) { %>checked<% } %> />
      No <input type="radio" name="powerSteering" value="0" <% if (new Integer(0).equals(ad.getPowerSteering())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Keyless Entry:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="keylessEntry" value="1" <% if (new Integer(1).equals(ad.getKeylessEntry())) { %>checked<% } %> />
      No <input type="radio" name="keylessEntry" value="0" <% if (new Integer(0).equals(ad.getKeylessEntry())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">TV/VCR/DVD:</span></th>
      <td class="edit-bg2"><span class="edit-text">
      Yes <input type = "radio" name="tvVcrDvd" value="1" <% if (new Integer(1).equals(ad.getTvVcrDvd())) { %>checked<% } %> />
      No <input type="radio" name="tvVcrDvd" value="0" <% if (new Integer(0).equals(ad.getTvVcrDvd())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Bucket Seats:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="bucketSeats" value="1" <% if (new Integer(1).equals(ad.getBucketSeats())) { %>checked<% } %> />
      No <input type="radio" name="bucketSeats" value="0" <% if (new Integer(0).equals(ad.getBucketSeats())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Leather Interior:</span></th>
      <td class="edit-bg2"><span class="edit-text">
      Yes <input type = "radio" name="leatherInterior" value="1" <% if (new Integer(1).equals(ad.getLeatherInterior())) { %>checked<% } %> />
      No <input type="radio" name="leatherInterior" value="0" <% if (new Integer(0).equals(ad.getLeatherInterior())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Memory Seats:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="memorySeats" value="1" <% if (new Integer(1).equals(ad.getMemorySeats())) { %>checked<% } %> />
      No <input type="radio" name="memorySeats" value="0" <% if (new Integer(0).equals(ad.getMemorySeats())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Power Seats:</span></th>
      <td class="edit-bg2"><span class="edit-text">
      Yes <input type = "radio" name="powerSeats" value="1" <% if (new Integer(1).equals(ad.getPowerSeats())) { %>checked<% } %> />
      No <input type="radio" name="powerSeats" value="0" <% if (new Integer(0).equals(ad.getPowerSeats())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Driver Airbag:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="driverAirbag" value="1" <% if (new Integer(1).equals(ad.getDriverAirbag())) { %>checked<% } %> />
      No <input type="radio" name="driverAirbag" value="0" <% if (new Integer(0).equals(ad.getDriverAirbag())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Passenger Airbag:</span></th>
      <td class="edit-bg2"><span class="edit-text">
      Yes <input type = "radio" name="passengerAirbag" value="1" <% if (new Integer(1).equals(ad.getPassengerAirbag())) { %>checked<% } %> />
      No <input type="radio" name="passengerAirbag" value="0" <% if (new Integer(0).equals(ad.getPassengerAirbag())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Side Airbag:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="sideAirbag" value="1" <% if (new Integer(1).equals(ad.getSideAirbag())) { %>checked<% } %> />
      No <input type="radio" name="sideAirbag" value="0" <% if (new Integer(0).equals(ad.getSideAirbag())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Alarm:</span></th>
      <td class="edit-bg2"><span class="edit-text">
      Yes <input type = "radio" name="alarm" value="1" <% if (new Integer(1).equals(ad.getAlarm())) { %>checked<% } %> />
      No <input type="radio" name="alarm" value="0" <% if (new Integer(0).equals(ad.getAlarm())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Anti-lock Brakes:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="antiLockBrakes" value="1" <% if (new Integer(1).equals(ad.getAntiLockBrakes())) { %>checked<% } %> />
      No <input type="radio" name="antiLockBrakes" value="0" <% if (new Integer(0).equals(ad.getAntiLockBrakes())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Foglights:</span></th>
      <td class="edit-bg2"><span class="edit-text">
      Yes <input type = "radio" name="foglights" value="1" <% if (new Integer(1).equals(ad.getFoglights())) { %>checked<% } %> />
      No <input type="radio" name="foglights" value="0" <% if (new Integer(0).equals(ad.getFoglights())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Cassette Player:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="cassettePlayer" value="1" <% if (new Integer(1).equals(ad.getCassettePlayer())) { %>checked<% } %> />
      No <input type="radio" name="cassettePlayer" value="0" <% if (new Integer(0).equals(ad.getCassettePlayer())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">CD Changer:</span></th>
      <td class="edit-bg2"><span class="edit-text">
      Yes <input type = "radio" name="cdChanger" value="1" <% if (new Integer(1).equals(ad.getCdChanger())) { %>checked<% } %> />
      No <input type="radio" name="cdChanger" value="0" <% if (new Integer(0).equals(ad.getCdChanger())) { %>checked<% } %>/></span></td>   </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">CD Player:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="cdPlayer" value="1" <% if (new Integer(1).equals(ad.getCdPlayer())) { %>checked<% } %> />
      No <input type="radio" name="cdPlayer" value="0" <% if (new Integer(0).equals(ad.getCdPlayer())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Premium Sound:</span></th>
      <td class="edit-bg2"><span class="edit-text">
      Yes <input type = "radio" name="premiumSound" value="1" <% if (new Integer(1).equals(ad.getPremiumSound())) { %>checked<% } %> />
      No <input type="radio" name="premiumSound" value="0" <% if (new Integer(0).equals(ad.getPremiumSound())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Power Windows:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="powerWindows" value="1" <% if (new Integer(1).equals(ad.getPowerWindows())) { %>checked<% } %> />
      No <input type="radio" name="powerWindows" value="0" <% if (new Integer(0).equals(ad.getPowerWindows())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Rear Win Defogger:</span></th>
      <td class="edit-bg2"><span class="edit-text">
      Yes <input type = "radio" name="rearWinDefogger" value="1" <% if (new Integer(1).equals(ad.getRearWinDefogger())) { %>checked<% } %> />
      No <input type="radio" name="rearWinDefogger" value="0" <% if (new Integer(0).equals(ad.getRearWinDefogger())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Rear Win Wiper:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="rearWinWiper" value="1" <% if (new Integer(1).equals(ad.getRearWinWiper())) { %>checked<% } %> />
      No <input type="radio" name="rearWinWiper" value="0" <% if (new Integer(0).equals(ad.getRearWinWiper())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Tinted Glass:</span></th>
      <td class="edit-bg2"><span class="edit-text">
      Yes <input type = "radio" name="tintedGlass" value="1" <% if (new Integer(1).equals(ad.getTintedGlass())) { %>checked<% } %> />
      No <input type="radio" name="tintedGlass" value="0" <% if (new Integer(0).equals(ad.getTintedGlass())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Alloy Wheels:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="alloyWheels" value="1" <% if (new Integer(1).equals(ad.getAlloyWheels())) { %>checked<% } %> />
      No <input type="radio" name="alloyWheels" value="0" <% if (new Integer(0).equals(ad.getAlloyWheels())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Sunroof:</span></th>
      <td class="edit-bg2"><span class="edit-text">
      Yes <input type = "radio" name="sunroof" value="1" <% if (new Integer(1).equals(ad.getSunroof())) { %>checked<% } %> />
      No <input type="radio" name="sunroof" value="0" <% if (new Integer(0).equals(ad.getSunroof())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Moonroof:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="moonroof" value="1" <% if (new Integer(1).equals(ad.getMoonroof())) { %>checked<% } %> />
      No <input type="radio" name="moonroof" value="0" <% if (new Integer(0).equals(ad.getMoonroof())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Third Row Seats:</span></th>
      <td class="edit-bg2"><span class="edit-text">
      Yes <input type = "radio" name="thirdRowSeats" value="1" <% if (new Integer(1).equals(ad.getThirdRowSeats())) { %>checked<% } %> />
      No <input type="radio" name="thirdRowSeats" value="0" <% if (new Integer(0).equals(ad.getThirdRowSeats())) { %>checked<% } %>/></span></td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Tow Package:</span></th>
      <td class="edit-bg1"><span class="edit-text">
      Yes <input type = "radio" name="towPackage" value="1" <% if (new Integer(1).equals(ad.getTowPackage())) { %>checked<% } %> />
      No <input type="radio" name="towPackage" value="0" <% if (new Integer(0).equals(ad.getTowPackage())) { %>checked<% } %>/></span></td>
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
      <br><span class="edit-helptext">(Cars.com - Optional, <span class="edit-reqhelptext">Required for Combo Ads</span>)</span></td>
    </tr>
    <tr>
      <th colspan="2">&nbsp;</th>
    </tr>
    <tr>
      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Contact Information</span></th>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Contact Name:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "contactName" maxlength = "200" value = "<%= ad.getContactName() %>"/></span>
      <br><span class="edit-helptext">(Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Contact Phone:</span></th>
      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "16" name = "contactPhone" maxlength = "16" value = "<%= ad.getContactPhone() %>"/></span> <span class="edit-helptext">(xxx-xxx-xxxx)</span>
      <br><span class="edit-helptext">(Cars.com/Compressor - Optional)</span>
      </td>
    </tr>
    <tr>
      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Contact Email:</span></th>
      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "contactEmail" maxlength = "100" value = "<%= ad.getContactEmail() %>"/></span>
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