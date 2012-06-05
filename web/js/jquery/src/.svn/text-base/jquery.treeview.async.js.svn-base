/*
 * Async Treeview 0.1 - Lazy-loading extension for Treeview
 * 
 * http://bassistance.de/jquery-plugins/jquery-plugin-treeview/
 *
 * Copyright (c) 2007 Jörn Zaefferer
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * Revision: $Id$
 *
 */

;(function($) {

    function load(settings, child, container) {
        $.getJSON(settings.url,settings.parameters, function(response) {            
            function createNode(parent) {
                var current = $("<li/>").attr("id", this.id || "").html("<span>" + this.text + "</span>").appendTo(parent);
                
                current.addClass("sortable");

                if (this.classes) {
                    current.children("span").addClass(this.classes);
                }
                if (this.expanded) {
                    current.addClass("open");
                }
                if (this.producible) {
                    current.addClass("producible").addClass("file");
                }
                if(this.activo && this.hasStock){
                    current.addClass("activo ui-state-primary");
                } else if(this.leaf) {
                    current.addClass("inactivo ui-state-disabled");
                }
                if (this.leaf){
                    current.addClass("selectable ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only").hover(function() {
                        $(this).closest("li").addClass("hover ui-state-hover");
                    }, function() {
                        $(this).closest("li").removeClass("hover ui-state-hover");
                    });
                } else {
                    current.attr("treeType",settings.treeType);
                }
                if (this.text == "ROOT"){
                    current.addClass("rootNode").removeClass("sortable");
                }

                if (this.hasChildren || this.children && this.children.length) {
                    var branch = $("<ul/>").appendTo(current);
                    if (this.hasChildren) {
                        current.addClass("hasChildren");
                        createNode.call({
                            text:"placeholder",
                            id:"placeholder",
                            children:[]
                        }, branch);
                    }
                    if (this.children && this.children.length) {
                        current.addClass("parent");
                        $.each(this.children, createNode, [branch])
                    }
                }
            }
            if(settings.root){
                var root = [{
                    "children":[],
                    "id":-1,
                    "leaf":false,
                    "text":"ROOT"
                }];
                root[0].children = response;
                response = root;
            }
            $.each(response, createNode, [child]);
            $(container).treeview({
                add: child
            });
            if(settings.success)
                settings.success.apply();
        });
    }

    var proxied = $.fn.treeview;
    $.fn.treeview = function(settings) {
        if (!settings.url) {
            return proxied.apply(this, arguments);
        }        
        var container = this;        
        load(settings, this, container);
        var userToggle = settings.toggle;
        return proxied.call(this, $.extend({}, settings, {
            collapsed: true,
            toggle: function() {
                var $this = $(this);
                if ($this.hasClass("hasChildren")) {
                    var childList = $this.removeClass("hasChildren").find("ul");
                    childList.empty();
                    load(settings, this.id, childList, container);
                }
                if (userToggle) {
                    userToggle.apply(this, arguments);
                }
            }
        }));
    };

})(jQuery);