/*
 * jQuery doubleSelect Plugin
 * version: 1.1
 * @requires jQuery v1.3.2 or later
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * @version $Id: jquery.doubleSelect.js 3 2009-04-24 12:00:00Z $
 * @author  Johannes Geppert <post at jgeppert dot com> http://www.jgeppert.com
 */

/**
 * Converts passed JSON options into <select> elements.
 * 
 * @param String
 *            id of the second select box
 * @param String
 *            option values
 * @param array
 *            options additional options (optional)
 */

( function($) {
    $.fn.doubleSelect = function(doubleid, values, options) {

        options = $.extend( {
            preselectFirst :null,
            preselectSecond :null,
            emptyOption :false,
            emptyKey :-1,
            emptyValue :'Choose ...'
        }, options || {});

        var $first = this;
        var $secondid = "#" + doubleid;
        var $second = $($secondid);

        var setValue = function(value) {
            $second.val(value).change();
        };

        var removeValues = function() {
            $($secondid + " option").remove();
        };

        $(this).change( function() {
            removeValues();
            $current = this.options[this.selectedIndex].value;
            if ($current != '') {
                $("<option>").html("-- Seleccionar --").attr("value",-1).appendTo($second);
                $.each(values, function(k, v) {
                    if ($current == v.id) {
                        $.each(v.children, function(k, v2) {
                            var o;
                            if(v2.productosCount)
                                o = $("<option>").html(v2.text + " <b>(" + v2.productosCount + ")</b>").attr('value', v2.id);
                            else
                                o = $("<option>").html(v2.text).attr('value', v2.id);
                            if (v.defaultvalue != null && v2 == v.defaultvalue) {
                                o.html(k).attr("selected","selected");
                            }
                            if (options.preselectSecond != null && v2 == options.preselectSecond) {
                                o.html(k).attr("selected","selected");
                            }
                            o.appendTo($second);
                        });

                    }

                });

            } else {
                setValue(options.emptyValue);
            }
        });

        return this.each( function() {
            $first.children().remove();
            $second.children().remove();

            if (options.emptyOption) {
                var oe = $("<option>").html(options.emptyValue).attr('value', options.emptyKey);
                oe.appendTo($first);
            }

            $.each(values, function(k, v) {
                var of = $("<option>").html(v.text).attr('value', v.id);
                if (options.preselectFirst != null && v.id == options.preselectFirst) {
                    of.html(v.text).attr("selected","selected");
                }
                of.appendTo($first);

            });
			
            if (options.preselectFirst == null) {
                $current = this.options[this.selectedIndex].value;
                if ($current != '') {
                    $.each(values, function(k, v) {
                        if ($current == v.id) {
                            $.each(v.children, function(k, v2) {
                                var o = $("<option>").html(v2.text).attr('value', v2.id);
                                if (v.defaultvalue != null && v2 == v.defaultvalue) {
                                    o.html(k).attr("selected","selected");
                                }
                                if (options.preselectSecond != null && v2 == options.preselectSecond) {
                                    o.html(k).attr("selected","selected");
                                }
                                o.appendTo($second);
                            });
                        }

                    });

                } else {
                    setValue(options.emptyValue);
                }
            } else {
                $first.change();
            }

        });

    };
})(jQuery);
