$(document).ready(function () {
    // Disable search and ordering by default
    $.extend( $.fn.dataTable.defaults, {
        searching: true,
        ordering:  false
    });

    // For this specific table we are going to enable ordering
    // (searching is still disabled)
    $('#table-id').DataTable( {
        ordering: true
    });
})