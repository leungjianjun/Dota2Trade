var EditableTable = function () {

    return {

        //main function to initiate the module
        init: function () {
            function restoreRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);

                for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                    oTable.fnUpdate(aData[i], nRow, i, false);
                }

                oTable.fnDraw();
            }

            function editRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                jqTds[0].innerHTML = '<input type="text" disabled="disabled" class="form-control small" value="' + aData[1] + '">';
                jqTds[1].innerHTML = '<input type="text" class="form-control small" value="' + aData[2] + '">';
                jqTds[2].innerHTML = '<a class="edit" href="">保存</a>';
                jqTds[3].innerHTML = '<a class="cancel" href="">取消</a>';
            }

            function saveRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                oTable.fnUpdate(jqInputs[0].value, nRow, 1, false);
                oTable.fnUpdate(jqInputs[1].value, nRow, 2, false);
                oTable.fnUpdate('<a class="edit" href="">修改</a>', nRow, 3, false);
                oTable.fnUpdate('<a class="delete" href="">删除</a>', nRow, 4, false);
                oTable.fnDraw();
            }

            function cancelEditRow(oTable, nRow) {
                alert("cancel");
                var jqInputs = $('input', nRow);
                oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                oTable.fnUpdate('<a class="edit" href="">修改</a>', nRow, 2, false);
                oTable.fnUpdate('<a class="delete" href="">删除</a>', nRow, 3, false);
                oTable.fnDraw();
            }

            var oTable = $('#editable-sample').dataTable({
                "bProcessing": true,
                "aLengthMenu": [
                    [5, 15, 20, -1],
                    [5, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 5,
                "sDom": "<'row'<'col-lg-6'l><'col-lg-6'f>r>t<'row'<'col-lg-6'i><'col-lg-6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "每页 _MENU_ 记录",
                    "oPaginate": {
                        "sPrevious": "上页",
                        "sNext": "下页"
                    }
                },
                "aoColumns":[
                    {'sTitle':'ID', 'sClass':'center',"bVisible": false},
                    {'sTitle':'账号','sClass':'center'},
                    {'sTitle':'密码','sClass':'center'},
                    {'sTitle':'修改','sClass':'center'},
                    {'sTitle':'删除', 'sClass':'center'}
                ],
                "aoColumnDefs": [{
                        'bSortable': false,
                        'aTargets': [0]
                    }
                ]
            });

            jQuery('#editable-sample_wrapper .dataTables_filter input').addClass("form-control medium"); // modify table search input
            jQuery('#editable-sample_wrapper .dataTables_length select').addClass("form-control xsmall"); // modify table per page dropdown

            var nEditing = null;

            $('#editable-sample_new').click(function (e) {
                e.preventDefault();
                var aiNew = oTable.fnAddData(['', '<input type="text" class="form-control small" value="">', '<input type="text" class="form-control small" value="">',
                        '<a class="edit" data-mode="new" href="">保存</a>', '<a class="cancel" data-mode="new" href="">取消</a>'
                ]);
                var nRow = oTable.fnGetNodes(aiNew[0]);
                nEditing = nRow;
            });

            $('#editable-sample a.delete').live('click', function (e) {
                e.preventDefault();

                if (confirm("你确定要删除该记录 ?") == false) {
                    return;
                }

                var nRow = $(this).parents('tr')[0];
                oTable.fnDeleteRow(nRow);
                alert("已删除! Do not forget to do some ajax to sync with backend :)");
            });

            $('#editable-sample a.cancel').live('click', function (e) {
                e.preventDefault();
                if ($(this).attr("data-mode") == "new") {
                    var nRow = $(this).parents('tr')[0];
                    oTable.fnDeleteRow(nRow);
                } else {
                    restoreRow(oTable, nEditing);
                    nEditing = null;
                }
            });

            $('#editable-sample a.edit').live('click', function (e) {
                e.preventDefault();

                /* Get the row as a parent of the link that was clicked on */
                var nRow = $(this).parents('tr')[0];

                if (nEditing !== null && nEditing != nRow) {
                    /* Currently editing - but not this row - restore the old before continuing to edit mode */
                    restoreRow(oTable, nEditing);
                    editRow(oTable, nRow);
                    nEditing = nRow;
                } else if (nEditing == nRow && this.innerHTML == "保存") {
                    /* Editing this row and want to save it */
                    saveRow(oTable, nEditing);

                    var aData = oTable.fnGetData(nRow);
                    $.ajax({
                        type:'POST',
                        url:"/doUpdateUser",
                        data:{id:aData[0],account:aData[1],password:aData[2]},
                        success:function(data){
                            alert(data);
                        },
                        error:function(XMLHttpRequest, textStatus, errorThrown){
                            alert(XMLHttpRequest.status);
                            alert(XMLHttpRequest.readyState);
                            alert(textStatus);
                        }
                    });
                    nEditing = null;
                    alert("更新成功! Do not forget to do some ajax to sync with backend :)");
                } else {
                    /* No edit in progress - let's start one */
                    editRow(oTable, nRow);
                    nEditing = nRow;
                }
            });


        }

    };

}();