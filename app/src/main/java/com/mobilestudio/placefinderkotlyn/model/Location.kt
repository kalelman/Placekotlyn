package mx.mobilestudio.placefinderkotlin.model

import java.util.ArrayList


class Location {

    /**

     * @return
     * *     The lat
     */
    /**

     * @param lat
     * *     The lat
     */
    var lat: Double? = null
    /**

     * @return
     * *     The lng
     */
    /**

     * @param lng
     * *     The lng
     */
    var lng: Double? = null
    /**

     * @return
     * *     The labeledLatLngs
     */
    /**

     * @param labeledLatLngs
     * *     The labeledLatLngs
     */
    var labeledLatLngs: List<LabeledLatLng> = ArrayList()
    /**

     * @return
     * *     The distance
     */
    /**

     * @param distance
     * *     The distance
     */
    var distance: Int? = null
    /**

     * @return
     * *     The cc
     */
    /**

     * @param cc
     * *     The cc
     */
    var cc: String? = null
    /**

     * @return
     * *     The country
     */
    /**

     * @param country
     * *     The country
     */
    var country: String? = null
    /**

     * @return
     * *     The formattedAddress
     */
    /**

     * @param formattedAddress
     * *     The formattedAddress
     */
    var formattedAddress: List<String> = ArrayList()

}
