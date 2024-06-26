import React, { useEffect, useState } from 'react';
import { Card, CardFooter, Image, Button } from '@nextui-org/react';
import { Product } from './product'; // Import the Product type
import CreateProductModal from './CreateProductModal';
import ProductModal from '@/app/products/_components/ProductModal'; // Import the CreateProductModal component
import AddToCartButton from '@/components/cart/addToCart';
import process from 'process';
import {useSession} from 'next-auth/react';

const ProductCard: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [showProductForm, setShowProductForm] = useState(false);
    const { data: session, status } = useSession();

  useEffect(() => {
    // Fetch products from your API endpoint
    fetch(`${process.env.NEXT_PUBLIC_PRODUCTS_API_URL}/products`, {
        headers: {
            Authorization: 'Bearer ' + session?.access_token
        }
    })
      .then((response) => response.json())
      .then((data: Product[]) => setProducts(data));
  }, [session]);

  const handleAddProductClick = () => {
    setShowProductForm(true);
  };

  const handleProductCreate = (newProduct: Product) => {
    // Send a POST request to add the new product to your API
    fetch(`${process.env.NEXT_PUBLIC_PRODUCTS_API_URL}/products`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
          Authorization: 'Bearer ' + session?.access_token
      },
      body: JSON.stringify(newProduct)
    })
      .then((response) => response.json())
      .then((createdProduct) => {
        // Add the newly created product to the state
        setProducts([...products, createdProduct]);
      })
      .catch((error) => {
        console.error('Error adding product:', error);
      });
  };

  return (
    <div>
      <div className="text-right mb-4">
        <Button color="primary" onClick={handleAddProductClick}>
          Add Product
        </Button>
      </div>
      <div className="gap-2 grid grid-cols-2 sm:grid-cols-4">
        {products.map((product) => (
          <Card key={product.id} shadow="sm">
            <Image
              shadow="sm"
              radius="lg"
              width="100%"
              alt={product.name}
              className="w-full object-cover h-[300px]"
              src={product.image}
            />
            <CardFooter className="text-small">
              <h2>Name: {product.name}</h2>
              <p>Price: ${product.price.toFixed(2)}</p>
            </CardFooter>
            <ProductModal product={product} />
            <AddToCartButton product={product} />
          </Card>
        ))}
      </div>

      <CreateProductModal
        isOpen={showProductForm}
        onClose={() => setShowProductForm(false)}
        onProductCreate={handleProductCreate}
      />
    </div>
  );
};

export default ProductCard;
