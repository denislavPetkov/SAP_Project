<?php

function load_stock()
{
    $connect = mysqli_connect("localhost:8080", "root", "Lukoil12345^7", "database1");
    $output = '';
    $sql = "SELECT * FROM stock";
    $result = mysql_query($connect, $sql);
    while($row = mysql_fetch_array($result))
    {
        $output .= '<option value="'.$row["id"].'">'.$row["id"].'</option>';
    }
}
?>

<html>
    <head>
        <title>hello</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <p>Select Product
        <select name="product" id="product">
            <option value="">Select Product</option>
            <?php echo load_stock(); ?>
        </select></p>
        <p>Select Quantity
        <select name="quantity" id="quantity">
            <option value="">Select Quantity</option>
         </select></p>
    </body>
</html>

<script>
$(document).ready(function(){
    $('#product').change(function(){
        var product_id = $(this).val();
        s.ajax({
            url:"fetch_quantity.php",
            method:"POST",
            data:{productId:product_id},
            dataType:"text",
            success:function(data)
            {
                $('#quantity').html(data);
            }
        });
    });
});
</script>