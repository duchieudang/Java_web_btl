<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta content="width=device-width, initial-scale=1.0" name="viewport">
	
	<title>Quản lý khách hàng</title>
	<meta content="" name="description">
	<meta content="" name="keywords">
	
	<!-- Favicons -->
	<link href="assets/img/favicon.png" rel="icon">
	<link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">
	
	<!-- Google Fonts -->
	<link href="https://fonts.gstatic.com" rel="preconnect">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">
	
	<!-- Vendor CSS Files -->
	<link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
	<link href="assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
	<link href="assets/vendor/quill/quill.snow.css" rel="stylesheet">
	<link href="assets/vendor/quill/quill.bubble.css" rel="stylesheet">
	<link href="assets/vendor/remixicon/remixicon.css" rel="stylesheet">
	<link href="assets/vendor/simple-datatables/style.css" rel="stylesheet">
  
	 <!-- Template Main CSS File -->
	 <link href="assets/css/style.css" rel="stylesheet">
	 
 	<script>
	    function toggleInput(checkboxId, inputId) {
	        const checkbox = document.getElementById(checkboxId);
	        const input = document.getElementById(inputId);
	        input.disabled = !checkbox.checked;
	    }
	</script>
</head>

<body>
	<%@ include file="fragments/header.jsp" %>
	<%@ include file="fragments/sidebar.jsp" %>
	
	<main id="main" class="main">
	
		<div class="pagetitle">
			<h1>Quản lý khách hàng</h1>
			<nav>
				<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="index.html">Trang chủ</a></li>
				<li class="breadcrumb-item">Quản lý</li>
				<li class="breadcrumb-item active">Khách hàng</li>
			</ol>
			</nav>
		</div>
		<section class="section">
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
        				<div class="card-body">
        				
        				<div class="d-flex align-items-center justify-content-start gap-2 my-3">
						    <h5 class="mb-0">Bảng các khách hàng</h5>
						    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addModal">
						        Thêm
						    </button>
						    <select class="form-select w-auto" aria-label="Page selection" id="pageSelect">
						        <c:forEach var="page" begin="1" end="${totalPages}">
						            <option value="${page}" ${page == currentPage ? 'selected' : ''}>Trang ${page}</option>
						        </c:forEach>
						    </select>
						    <script>
							    document.addEventListener('DOMContentLoaded', () => {
							        const selectElement = document.getElementById('pageSelect');
							        const baseUrl = `${pageContext.request.contextPath}/customer?page=`;
							        selectElement.addEventListener('change', (event) => {
							            const selectedPage = event.target.value;
							            if (selectedPage) {
							                window.location.href = baseUrl + selectedPage;
							            }
							        });
							    });
							</script>
							<p class="mb-0">trên tổng số ${totalPages} trang.</p>		
							<p class="mb-0">Tổng số khách hàng: ${totalItems} </p>				    
						</div>
        				
        				
          				<c:if test="${not empty success}">
						    <span class="alert alert-success bg-success text-light border-0 alert-dismissible fade show position-fixed bottom-0 end-0 m-3" role="alert">
							    ${success}
							    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="alert" aria-label="Close"></button>
							</span>
						</c:if>
						<c:if test="${not empty failure}">
						    <span class="alert alert-danger bg-danger text-light border-0 alert-dismissible fade show position-fixed bottom-0 end-0 m-3" role="alert">
                				${failure}
                				<button type="button" class="btn-close btn-close-white" data-bs-dismiss="alert" aria-label="Close"></button>
              				</span>
						</c:if>
						<table class="table">
  							<thead>
								<tr>
									<th scope="col" class="align-middle">ID</th>
									<th scope="col" class="align-middle">Tài khoản</th>
									<th scope="col">Tên đầy đủ</th>
									<th scope="col">Điện thoại</th>
									<th scope="col">Email</th>
									<th scope="col">Địa chỉ</th>
									<th scope="col">Ngày sinh</th>
									<th scope="col">Giới tính</th>
									<th scope="col">Ghi chú</th>
									<th scope="col">Hành động</th>
								</tr>
							</thead>
  							<tbody>
  							<c:forEach var="row" items="${items}">
							    <tr>
							        <td class="align-middle">${row.customerId}</td>
							        <td class="align-middle">${row.customerUsername}</td>
							        <td class="align-middle">${row.customerFullname}</td>
							        <td class="align-middle">${row.customerPhone}</td>
							        <td class="align-middle">${row.customerEmail}</td>
							        <td class="align-middle">${row.customerAddress}</td>
							        <td class="align-middle">${row.customerBirthday}</td>
							        <td class="align-middle">${row.customerGender}</td>
							        <td class="align-middle">${row.customerNote}</td>
							        <td class="align-middle">
							            <!-- Delete Form -->
							            <form action="${pageContext.request.contextPath}/customer" method="post" style="display: inline;">
							                <input type="hidden" name="action" value="delete">
							                <input type="hidden" name="customer_id" value="${row.customerId}">
							                <button type="submit" class="btn btn-danger btn-action">
							                    Xóa
							                </button>
							            </form>
							
							            <!-- Edit Button (opens modal) -->
							            <button type="button" class="btn btn-warning btn-action" data-bs-toggle="modal" data-bs-target="#editModal${row.customerId}">
							                Sửa
							            </button>
							
							            <!-- Modal for Editing Customer -->
							            <div class="modal fade" id="editModal${row.customerId}" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
							                <div class="modal-dialog">
							                    <div class="modal-content">
							                        <div class="modal-header">
							                            <h5 class="modal-title" id="editPhongModalLabel">
							                                Sửa thông tin khách hàng có ID: ${row.customerId}
							                            </h5>
							                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							                        </div>
							                        <div class="modal-body">
							                            <form action="${pageContext.request.contextPath}/customer" method="post" enctype="multipart/form-data">
							                                <input type="hidden" name="action" value="update">
							                                <input type="hidden" name="customer_id" value="${row.customerId}">
							
							                                <!-- Form fields for editing customer info -->
							                                <div class="mb-3">
							                                    <label for="customer_username" class="form-label">Tài khoản</label>
							                                    <input type="text" class="form-control" id="customer_username" name="customer_username" value="${row.customerUsername}" required>
							                                </div>
							
							                                <div class="mb-3">
							                                    <span class="form-switch me-${row.customerId}">
							                                        <input class="form-check-input" type="checkbox" id="is_update_customer_password${row.customerId}" name="is_update_customer_password" onchange="toggleInput('is_update_customer_password${row.customerId}', 'customer_password${row.customerId}')">
							                                    </span>
							                                    <label for="customer_password${row.customerId}" class="form-label">Mật khẩu mới</label>
							                                    <input disabled type="password" class="form-control" id="customer_password${row.customerId}" name="customer_password" required>
							                                </div>
							
							                                <!-- Other form fields for customer details -->
							                                <div class="mb-3">
							                                    <label for="customer_fullname" class="form-label">Tên đầy đủ</label>
							                                    <input type="text" class="form-control" id="customer_fullname" name="customer_fullname" value="${row.customerFullname}" required>
							                                </div>
							
							                                <div class="mb-3">
							                                    <label for="customer_phone" class="form-label">Điện thoại</label>
							                                    <input type="tel" class="form-control" id="customer_phone" name="customer_phone" value="${row.customerPhone}" required>
							                                </div>
							
							                                <div class="mb-3">
							                                    <label for="customer_email" class="form-label">Email</label>
							                                    <input type="email" class="form-control" id="customer_email" name="customer_email" value="${row.customerEmail}" required>
							                                </div>
							
							                                <div class="mb-3">
							                                    <label for="customer_address" class="form-label">Địa chỉ</label>
							                                    <input type="text" class="form-control" id="customer_address" name="customer_address" value="${row.customerAddress}" required>
							                                </div>
							
							                                <div class="mb-3">
							                                    <label for="customer_birthday" class="form-label">Ngày sinh</label>
							                                    <input type="date" class="form-control" id="customer_birthday" name="customer_birthday" value="${row.customerBirthday}" required>
							                                </div>
							
							                                <div class="mb-3">
							                                    <label for="customer_gender" class="form-label">Giới tính</label>
							                                    <input type="text" class="form-control" id="customer_gender" name="customer_gender" value="${row.customerGender}" required>
							                                </div>
							
							                                <div class="mb-3">
							                                    <label for="customer_note" class="form-label">Mô tả</label>
							                                    <textarea class="form-control" id="customer_note" name="customer_note" rows="3" required>${row.customerNote}</textarea>
							                                </div>
							
							                                <button type="submit" class="btn btn-warning">Sửa</button>
							                            </form>
							                        </div>
							                    </div>
							                </div>
							            </div>
							        </td>
							    </tr>
							</c:forEach>

  							</tbody>
						</table>

        				</div>
      				</div>
    			</div>
  			</div>
		</section>
	</main><!-- End #main -->
	
	<div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addPhongModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addPhongModalLabel">Thêm khách hàng</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="${pageContext.request.contextPath}/customer" method="post" enctype="multipart/form-data">
						<input type="hidden" name="action" value="add">
						<input type="hidden" name="customer_id" value="${row['customer_id']}">

						<div class="mb-3">
							<label for="customer_username" class="form-label">Tài khoản</label>
							<input type="text" class="form-control" id="customer_username" name="customer_username" required>
						</div>
						
						<div class="mb-3">
							<label for="customer_password" class="form-label">Mật khẩu</label>
							<input type="password" class="form-control" id="customer_password" name="customer_password" required>
						</div>

						<div class="mb-3">
							<label for="customer_fullname" class="form-label">Tên đầy đủ</label>
							<input type="text" class="form-control" id="customer_fullname" name="customer_fullname" required>
						</div>

						<div class="mb-3">
							<label for="customer_phone" class="form-label">Điện thoại</label>
							<input type="tel" class="form-control" id="customer_phone" name="customer_phone" required>
						</div>

						<div class="mb-3">
							<label for="customer_email" class="form-label">Email</label>
							<input type="email" class="form-control" id="customer_email" name="customer_email" required>
						</div>
						
						<div class="mb-3">
							<label for="customer_address" class="form-label">Địa chỉ</label>
							<input type="text" class="form-control" id="customer_address" name="customer_address" required>
						</div>
						
						<div class="mb-3">
							<label for="customer_birthday" class="form-label">Ngày sinh</label>
							<input type="date" class="form-control" id="customer_birthday" name="customer_birthday" required>
						</div>
						
						<div class="mb-3">
							<label for="customer_gender" class="form-label">Giới tính</label>
							<input type="text" class="form-control" id="customer_gender" name="customer_gender" required>
						</div>

						<!-- mô tả -->
						<div class="mb-3">
							<label for="customer_note" class="form-label">Mô tả</label>
							<textarea class="form-control" id="customer_note" name="customer_note" rows="3" required>
								${row['customer_note']}
							</textarea>
						</div>
						<button type="submit" class="btn btn-success">Thêm</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	
	 <!-- ======= Footer ======= -->
	<footer id="footer" class="footer">
	  <!-- <div class="copyright">
	    &copy; Copyright <strong><span>NiceAdmin</span></strong>. All Rights Reserved
	  </div>
	  <div class="credits">
	    Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
	  </div> -->
	</footer>
	<!-- End Footer -->
	
	<!-- Vendor JS Files -->
	<script src="assets/vendor/apexcharts/apexcharts.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script src="assets/vendor/chart.js/chart.umd.js"></script>
	<script src="assets/vendor/echarts/echarts.min.js"></script>
	<script src="assets/vendor/quill/quill.js"></script>
	<script src="assets/vendor/simple-datatables/simple-datatables.js"></script>
	<script src="assets/vendor/tinymce/tinymce.min.js"></script>
	<script src="assets/vendor/php-email-form/validate.js"></script>
	
	<!-- Template Main JS File -->
	<script src="assets/js/main.js"></script>

</body>

</html>