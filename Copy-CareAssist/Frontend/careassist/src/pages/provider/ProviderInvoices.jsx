import { useEffect, useState } from "react";
import { getInvoicesByProvider } from "../../services/invoice/invoiceService";



function ProviderInvoices() {
    
    const [invoices, setInvoices] = useState([]);
    useEffect(() => {

        loadInvoices();

    }, []);

    const loadInvoices = async () => {

        try {

            const providerId = localStorage.getItem("providerId");

            const response = await getInvoicesByProvider(providerId);

            setInvoices(response);

        } catch (error) {

            console.error(error);

            alert("Failed to load invoices");

        }

    };

    return (

        <div className="container mt-4">

            <h2 className="mb-4">
                Generated Invoices
            </h2>

            <div className="table-responsive">

                <table className="table table-bordered table-hover">

                    <thead className="table-dark">

                        <tr>

                            <th>Invoice ID</th>

                            <th>Invoice Number</th>

                            <th>Patient ID</th>

                            <th>Provider ID</th>

                            <th>Total Amount</th>

                            <th>Status</th>

                            <th>Invoice Date</th>

                        </tr>

                    </thead>

                    <tbody>

                        {
                            invoices.length === 0 ? (

                                <tr>

                                    <td
                                        colSpan="7"
                                        className="text-center"
                                    >
                                        No invoices found
                                    </td>

                                </tr>

                            ) : (

                                invoices.map((invoice) => (

                                    <tr key={invoice.invoiceId}>

                                        <td>{invoice.invoiceId}</td>

                                        <td>{invoice.invoiceNumber}</td>

                                        <td>{invoice.patientId}</td>

                                        <td>{invoice.providerId}</td>

                                        <td>₹ {invoice.totalAmount}</td>

                                        <td>{invoice.status}</td>

                                        <td>{invoice.invoiceDate}</td>

                                    </tr>

                                ))

                            )
                        }

                    </tbody>

                </table>

            </div>

        </div>

    );

}

export default ProviderInvoices;