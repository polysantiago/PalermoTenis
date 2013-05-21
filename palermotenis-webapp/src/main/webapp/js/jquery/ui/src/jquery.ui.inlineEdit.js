(function($) {

    var uiInlineEditClasses = 'ui-inlineedit-content ui-widget ui-widget-content ui-corner-all',
    highlight = 'ui-state-highlight';

    $.widget('ui.inlineEdit', {
        _init: function() {
            if (!this.value($.trim(this.element.text()) || this.options.value)) {
                this.element.html($(this._placeholderHtml()));
            }
            this._delegate();
        },
        _delegate: function() {
            var self = this;

            this.element
            .rightClick( function(event) {
                var $this = $(event.target);

                if ($this.hasClass('ui-inlineedit-save') || $this.parent().hasClass('ui-inlineedit-save')) {
                    self._save(self.element.find('.ui-inlineedit-input').val(), event);
                    return false;
                }

                if ($this.hasClass('ui-inlineedit-cancel') || $this.parent().hasClass('ui-inlineedit-cancel')) {
                    self._cancel(event, self.element.find('.ui-inlineedit-input').val());
                    return false;
                }
								
                if ($this.hasClass('ui-inlineedit') || $this.hasClass('ui-inlineedit-placeholder')) {
                    self._render();
                    return false;
                }
            })
            .bind('mouseover', function(event) {
                var $this = $(event.target);
				
                self.element.removeClass(highlight);
                self.element.find('.ui-inlineedit-button').removeClass('ui-state-hover');
				
                if ($this.hasClass('ui-inlineedit-save') || $this.parent().hasClass('ui-inlineedit-save')) {
                    self.element.find('.ui-inlineedit-save').addClass('ui-state-hover');
                    return;
                }
				
                if ($this.hasClass('ui-inlineedit') || $this.hasClass('ui-inlineedit-placeholder')) {
                    self.element.addClass(highlight);
                    return;
                }
            })
            .bind('mouseout', function(event) {
                var $this = $(event.target);
				
                if ($this.hasClass('ui-inlineedit-save') || $this.parent().hasClass('ui-inlineedit-save')) {
                    self.element.find('.ui-inlineedit-save').removeClass('ui-state-hover');
                    return;
                }
				
                if ($this.hasClass('ui-inlineedit') || $this.hasClass('ui-inlineedit-placeholder')) {
                    self.element.removeClass(highlight);
                    return;
                }
            })
            .addClass('ui-inlineedit');
        },
        _uiInlineEditHtml: function() {
            return '<form class="'+ uiInlineEditClasses +'">' +
            '<input class="ui-inlineedit-input" type="text" value="'+ this.value() +'"> '+
            '<a href="#" class="ui-inlineedit-save ui-inlineedit-button ui-state-default" title="'+ this.options.saveButton +'"><span class="ui-icon ui-icon-disk"></span>'+ this.options.saveButton +'</a>' +
            '<a href="#" class="ui-inlineedit-cancel ui-inlineedit-button ui-state-default" title="'+ this.options.cancelButton +'"><span class="ui-icon ui-icon-cancel"></span>'+ this.options.cancelButton +'</a>' +
            '</form>';
        },
        _placeholderHtml: function() {
            return '<span class="ui-inlineedit-placeholder">'+ this.options.placeholder +'</span>';
        },
        _render: function() {
            this.element
            .html(this._uiInlineEditHtml());
            this._complete();
            this._formSubmit();
        },
        _formSubmit: function() {
            var self = this;

            this.element.find('form')
            .submit(function(event) {
                self._save($('input', this).val(), event);
                $('input', this).blur();
                return false;
            });
        },
        _complete: function() {
            var self = this;
            self.element
            .find('input')
            .bind('blur', function() {
                if (self.timer) {
                    window.clearTimeout(self.timer);
                }
                self.timer = window.setTimeout(function() {
                    self.element.html(self.value() || self._placeholderHtml());
                    self.element.removeClass(highlight);
                }, 200);
            })
            .focus();
        },
        _save: function(newValue, event) {
            if (this._trigger('save', event, {
                value: newValue
            }) !== false) {
                this.value(newValue);
            }
        },
        _cancel: function(event) {
            this._trigger('cancel', event);
        },
	
        value: function(newValue) {
            if (arguments.length) {
                this._setOption('value', $(document.getElementById(newValue)).hasClass('ui-inline-edit-placeholder') ? '' : newValue);
            }
            return this.options.value;
        }
    });

    $.extend($.ui.inlineEdit, {
        version: "@VERSION",
        eventPrefix: "edit",
        defaults: {
            value: '',
            saveButton: 'Save',
            cancelButton: 'Cancel',
            placeholder: 'Click to edit'
        }
    });

})(jQuery);