import {define} from "ace-builds";

define("ace/mode/oclcomplete_highlight_rules",["require","exports","module","ace/lib/oop","ace/mode/text_highlight_rules"], function(require, exports, module) {
"use strict";

var oop = require("../lib/oop");
var TextHighlightRules = require("./text_highlight_rules").TextHighlightRules;

var oclcompleteHighlightRules = function() {
 
    // OCL keywords from eclipse implementation, complete the other highlighting rules TODO.
    var keywords = (
        "package|endpackage|context|inv|pre|post|body|derive|init|def|@pre|self|result|true|false|null|OclInvalid|and|or|xor|implies|not|let|in|if|then|else|endif|attr|oper"
    );

    var builtinConstants = (
        "true|false|self"
    );

    var builtinFunctions = (
        ""
    );

    var dataTypes = (
        "Any|String|Integer|Real|Boolean|Native|Bag|Set|List|Sequence|Map|OrderedSet|Collection|Tuple|ConcurrentBag|ConcurrentMap|ConcurrentSet"
    );

    var keywordMapper = this.createKeywordMapper({
        "support.function": builtinFunctions,
        "keyword": keywords,
        "constant.language": builtinConstants,
        "storage.type": dataTypes
    }, "identifier", true);

    this.$rules = {
        "start" : [ {
            token : "comment",
            regex : "//.*$"
        },  {
            token : "comment",
            start : "/\\*",
            end : "\\*/"
        }, {
            token : "string",           // " string
            regex : '".*?"'
        }, {
            token : "string",           // ' string
            regex : "'.*?'"
        }, {
            token : "string",           // ` string (apache drill)
            regex : "`.*?`"
        }, {
            token : "constant.numeric", // float
            regex : "[+-]?\\d+(?:(?:\\.\\d*)?(?:[eE][+-]?\\d+)?)?\\b"
        }, {
            token : keywordMapper,
            regex : "[a-zA-Z_$][a-zA-Z0-9_$]*\\b"
        }, {
            token : "text",
            regex : "\\s+"
        } ]
    };
    this.normalizeRules();
};

oop.inherits(oclcompleteHighlightRules, TextHighlightRules);

exports.oclcompleteHighlightRules = oclcompleteHighlightRules;
});

define("ace/mode/oclcomplete",["require","exports","module","ace/lib/oop","ace/mode/text","ace/mode/oclcomplete_highlight_rules"], function(require, exports, module) {
"use strict";

var oop = require("../lib/oop");
var TextMode = require("./text").Mode;
var oclcompleteHighlightRules = require("./oclcomplete_highlight_rules").oclcompleteHighlightRules;

var Mode = function() {
    this.HighlightRules = oclcompleteHighlightRules;
    this.$behaviour = this.$defaultBehaviour;
};
oop.inherits(Mode, TextMode);

(function() {
    this.$id = "ace/mode/oclcomplete";
    this.snippetFileId = "ace/snippets/oclcomplete";
}).call(Mode.prototype);

exports.Mode = Mode;

});
